package impl.listeners;

import core.Selectable;
import impl.Node;
import impl.panels.SimulationPanel;

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
    
    public SimulationPanelListener(SimulationPanel panel) {
        this.panel = panel;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
//            selectedItem = SimulationPanel.this.nodes.stream().findFirst().get();
        for (Node n : panel.getGraph().getNodes()) {
            if (n.isSelected(e.getPoint())) {
                selectedItem = n;
                dragging = true;
                return;
            }
        }
        // distance to mouse for all draggables/selectables
        for (Node n : panel.getGraph().getNodes()) {
            n.dx = e.getPoint().getX() - n.x;
            n.dy = e.getPoint().getY() - n.y;
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        selectedItem = null;
        dragging = false;
        dx = null; dy = null;
        
        for (Node n : panel.getGraph().getNodes()) {
            n.dx = 0;
            n.dy = 0;
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
//            System.out.println("dragging");
        panel.getMouse().setLocation(e.getPoint());
        
        if (selectedItem != null) {
            Point2D itemLocation = selectedItem.getLocation();
            if (dx == null) dx = e.getPoint().getX() - itemLocation.getX();
            if (dy == null) dy = e.getPoint().getY() - itemLocation.getY();
            selectedItem.moveTo(new Point2D.Double(
                    e.getPoint().getX() - dx,
                    e.getPoint().getY() - dy));
//                SimulationPanel.this.repaint();
            return;
        }
        
        // nothing was selected, so drag all elements
        for (Node n : panel.getGraph().getNodes()) {
            Point2D itemLocation = n.getLocation();
            if (dx == null) dx = e.getPoint().getX() - itemLocation.getX();
            if (dy == null) dy = e.getPoint().getY() - itemLocation.getY();
            n.moveTo(new Point2D.Double(
                    e.getPoint().getX() - n.dx,
                    e.getPoint().getY() - n.dy));
//                SimulationPanel.this.repaint();
        }
    }
    
    double scale = 1;
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // bad zooming
        // mouse de-sync and speed problems
        
        Point2D p1 = e.getPoint();
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
    }
    
    Node selectedNode1 = null;
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // connect two nodes
        if (selectedNode1 != null) {
            panel.getGraph().getNodes().stream()
                    .filter(n -> n.isSelected(e.getPoint()))
                    .findAny()
                    .ifPresent(n  -> panel.getGraph().addEdge(n, selectedNode1));
            panel.stopDrawingPotentialEdge();
            selectedNode1 = null;
            return;
        }
        // see what node was clicked, if any
        selectedNode1 =  panel.getGraph().getNodes().stream()
                .filter(n -> n.isSelected(e.getPoint()))
                .findAny().orElse(null);
        if (selectedNode1 != null) {
            panel.startDrawingPotentialEdge(selectedNode1);
        }
    }
    
    @Override public void mouseMoved(MouseEvent e) {
        panel.getMouse().setLocation(e.getPoint());
    }
    
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
}
