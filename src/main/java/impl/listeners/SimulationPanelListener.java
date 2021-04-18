package impl.listeners;

import core.Selectable;
import impl.Graph;
import impl.Node;
import impl.panels.SimulationPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Optional;

public class SimulationPanelListener implements MouseListener, MouseMotionListener, MouseWheelListener {
    
    boolean dragging = false;
    Double dx, dy;
    Selectable selectedItem;
    SimulationPanel panel;
    Graph graph;
    Point mouse;
    
    public SimulationPanelListener(SimulationPanel panel) {
        this.panel = panel;
        this.graph = panel.getGraph();
        mouse = new Point(0, 0);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        for (Node n : graph.getNodes()) {
            if (n.isSelected(mouse)) {
                selectedItem = n;
                dragging = true;
                return;
            }
        }
        // distance to mouse for all draggables/selectables
        for (Node n : graph.getNodes()) {
            n.dx = mouse.x - n.x;
            n.dy = mouse.y - n.y;
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        selectedItem = null;
        dragging = false;
        dx = null; dy = null;

        for (Node n : graph.getNodes()) {
            n.dx = 0;
            n.dy = 0;
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
//            System.out.println("dragging");
        //panel.getMouse().setLocation(e.getPoint());
        
        mouse.setLocation(e.getPoint().x / scale, e.getPoint().y / scale);
        
        if (selectedItem != null) {
            Point2D itemLocation = selectedItem.getLocation();
            if (dx == null) dx = mouse.x - itemLocation.getX();
            if (dy == null) dy = mouse.y - itemLocation.getY();
            selectedItem.moveTo(new Point2D.Double(
                    mouse.x - dx,
                    mouse.y - dy));
            return;
        }
        
        // nothing was selected, so drag all elements
        for (Node n : panel.getGraph().getNodes()) {
            Point2D itemLocation = n.getLocation();
            if (dx == null) dx = mouse.x - itemLocation.getX();
            if (dy == null) dy = mouse.y - itemLocation.getY();
            n.moveTo(new Point2D.Double(
                    mouse.x - n.dx,
                    mouse.y - n.dy));
        }
    }
    
    double scale = 1;
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // bad zooming
        // mouse de-sync and speed problems
    
        Point2D p1 = mouse;
        Point2D p2 = null;
        AffineTransform atx = panel.getAffineTransformation();
        try {
            p2 = atx.inverseTransform(p1, null);
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
            return;
        }
    
        scale -= (0.1 * e.getWheelRotation());
        scale = Math.max(0.1, scale);
    
        atx.setToIdentity();
        atx.translate(p1.getX(), p1.getY());
        atx.scale(scale, scale);
        atx.translate(-p2.getX(), -p2.getY());
        
        mouse.setLocation(e.getPoint().x / scale, e.getPoint().y / scale);
//        mouse.setLocation(e.getPoint());
    }
    
    Node selectedNode1 = null;
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // connect two nodes
        if (selectedNode1 != null) {
            getHoveredOverNode()
                    .ifPresent(n  -> graph.addEdge(n, selectedNode1));
            panel.stopDrawingPotentialEdge();
            selectedNode1 = null;
            return;
        }
        // see what node was clicked, if any
        getHoveredOverNode().ifPresentOrElse(
                n -> {
                    selectedNode1 = n;
                    panel.startDrawingPotentialEdge(selectedNode1); },
                () -> selectedNode1 = null);
    }
    
    public Point getMouse() {
        return this.mouse;
    }
    
    @Override public void mouseMoved(MouseEvent e) {
        //System.out.println("move");
        panel.getMouse().setLocation(e.getPoint().x / scale, e.getPoint().y / scale);
    }
    
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    
    private Optional<Node> getHoveredOverNode() {
        return graph.getNodes().stream()
                .filter(n -> n.isSelected(mouse))
                .findAny();
    }
}
