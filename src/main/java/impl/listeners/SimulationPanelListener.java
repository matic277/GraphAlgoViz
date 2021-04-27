package impl.listeners;

import impl.MyGraph;
import impl.Node;
import impl.panels.SimulationPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

/**
 * Zooming and panning adapted from
 * <a href="http://web.eecs.utk.edu/~bvanderz/gui/notes/transforms/PanAndZoom.java">http://web.eecs.utk.edu/~bvanderz/gui/notes/transforms/PanAndZoom.java</a>
 */
public class SimulationPanelListener implements MouseListener, MouseMotionListener, MouseWheelListener {
    
    SimulationPanel panel;
    MyGraph graph;
    Point mouse;
    
    // panning & zooming vars
    public double translateX = 0;
    public double translateY = 0;
    public double scale = 1;
    
    public double referenceX;
    public double referenceY;
    AffineTransform initialTransform;
    public Point2D XFormedPoint;
    
    Node selectedItem;
    Double dx, dy;
    
    public SimulationPanelListener(SimulationPanel panel) {
        this.panel = panel;
        this.graph = panel.getGraph();
        mouse = new Point(0, 0);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        mouse.setLocation(e.getPoint().x, e.getPoint().y);
    
        selectedItem = getHoveredOverNode();
        if (selectedItem != null) {
            dx = (mouse.getX() - selectedItem.x);
            dy = (mouse.getY() - selectedItem.y);
            return;
        }
        
        try {
            XFormedPoint = panel.atx.inverseTransform(e.getPoint(), null);
        }
        catch (NoninvertibleTransformException te) {
            System.out.println(te);
        }
        
        referenceX = XFormedPoint.getX();
        referenceY = XFormedPoint.getY();
        initialTransform = panel.atx;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        selectedItem = null;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        mouse.setLocation(e.getPoint().x, e.getPoint().y);
    
        if (selectedItem != null) {
            selectedItem.moveTo((int)(mouse.getX() - dx), (int)(mouse.getY() - dy));
            return;
        }
        
        try {
            XFormedPoint = initialTransform.inverseTransform(e.getPoint(), null);
        }
        catch (NoninvertibleTransformException te) {
            System.out.println(te);
        }
        
        double deltaX = XFormedPoint.getX() - referenceX;
        double deltaY = XFormedPoint.getY() - referenceY;
        
        referenceX = XFormedPoint.getX();
        referenceY = XFormedPoint.getY();
        
        translateX += deltaX;
        translateY += deltaY;
        
        panel.atx.translate(translateX, translateY);
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouse.setLocation(e.getPoint().x, e.getPoint().y);
        
        Point2D p1 = mouse;
        Point2D p2;
        try {
            p2 = panel.atx.inverseTransform(p1, null);
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
            return;
        }
        
        scale -= (0.1 * e.getWheelRotation());
        scale = Math.max(0.1, scale);
        
        panel.atx.setToIdentity();
        panel.atx.translate(p1.getX(), p1.getY());
        panel.atx.scale(scale, scale);
        panel.atx.translate(-p2.getX(), -p2.getY());
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
//        // connect two nodes
//        if (selectedNode1 != null) {
//            getHoveredOverNode()
//                    .ifPresent(n  -> graph.addEdge(n, selectedNode1));
//            panel.stopDrawingPotentialEdge();
//            selectedNode1 = null;
//            return;
//        }
//        // see what node was clicked, if any
//        getHoveredOverNode().ifPresentOrElse(
//                n -> {
//                    selectedNode1 = n;
//                    panel.startDrawingPotentialEdge(selectedNode1); },
//                () -> selectedNode1 = null);
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        mouse.setLocation(e.getPoint().x, e.getPoint().y);
    }
    
    private Node getHoveredOverNode() {
        return graph.getNodes().stream()
                .filter(n -> n.isSelected(mouse))
                .findAny()
                .orElse(null);
    }
    
    public Point getMouse() {
        return this.mouse;
    }
    
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
}
