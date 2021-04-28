package impl.listeners;

import impl.MyGraph;
import impl.Node;
import impl.panels.SimulationPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
    
    final JLabel nodeInfoLbl = new JLabel("Empty");
    
    public SimulationPanelListener(SimulationPanel panel) {
        this.panel = panel;
        this.graph = panel.getGraph();
        mouse = new Point(0, 0);
        
        nodeInfoLbl.setBounds(0, 0, 125,100);
        nodeInfoLbl.setBackground(new Color(255, 255, 255, 230));
        nodeInfoLbl.setBorder(new LineBorder(Color.black, 2));
        nodeInfoLbl.setOpaque(true);
        nodeInfoLbl.setVisible(false);
        panel.add(nodeInfoLbl);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        mouse.setLocation(e.getPoint().x, e.getPoint().y);
        
        selectedItem = getHoveredOverNode();
        nodeInfoLbl.setVisible(false);
        
        // RIGHT_CLICK
        if (e.getButton() == MouseEvent.BUTTON3 && selectedItem != null) {
            Point newPos = new Point(
                    (int)(selectedItem.ts.getBounds().getLocation().getX() + selectedItem.ts.getBounds().getWidth()),
                    (int)(selectedItem.ts.getBounds().getLocation().getY()));
            System.out.println(newPos);
//            nodeInfoLbl.getBounds().setLocation(newPos);
            nodeInfoLbl.setBounds(new Rectangle(newPos.x, newPos.y, nodeInfoLbl.getBounds().width, nodeInfoLbl.getBounds().height));
            nodeInfoLbl.setVisible(true);
            nodeInfoLbl.setText("<html>Node id="+selectedItem.getId()+"<br><br>State="+selectedItem.getState().getState()+"</html>");
            return;
        }
    
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
        
        // RIGHT_CLICK
        // Don't drag on right click.
        if (SwingUtilities.isRightMouseButton(e)) return;
        
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
    
        // TODO: not working (resizing info label)
//        nodeInfoLbl.setBounds(
//                nodeInfoLbl.getBounds().x,
//                nodeInfoLbl.getBounds().y,
//                (int)(nodeInfoLbl.getBounds().width / scale),
//                (int)(nodeInfoLbl.getBounds().height / scale));
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
