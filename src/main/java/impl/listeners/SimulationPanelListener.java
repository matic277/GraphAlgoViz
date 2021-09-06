package impl.listeners;

import impl.AlgorithmController;
import impl.MyGraph;
import impl.Node;
import impl.panels.simulationPanels.SimulationPanel;
import impl.tools.Tools;

import javax.swing.*;
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
    
    Node rightClickedNode; // for innerBtn informing capabilities (this could have been done differently)
    Node selectedItem1; // panning
    Node selectedItem2; // creating edges
    Double dx, dy;
    
    // TODO: these should be in the simWindow or simPanel
    final JLabel nodeInfoLbl;
    // inner buttons of nodeInfoLbl
    public final JButton informBtn = new JButton("Inform");
    public final JButton deleteNodeBtn = new JButton("Delete");
    
    public SimulationPanelListener(SimulationPanel panel) {
        this.panel = panel;
        this.graph = panel.getGraph();
        this.initialTransform = panel.atx;
        this.mouse = new Point(0, 0);
        
        // make it rounded
        nodeInfoLbl = new JLabel("Empty") {
            final Color actualBgColor = UIManager.getColor("Panel.background");
            @Override public void paintComponent(Graphics g) {
                g.setColor(actualBgColor);
                g.fillRoundRect(1, 1, this.getWidth()-2, this.getHeight()-2, 15, 15); // these should satisfy nodeInfoLbl.setBorder() arches and vice-versa
                super.paintComponent(g);
            }
        };
        nodeInfoLbl.setBackground(new Color(0, 0, 0, 0)); // must be set to transparent
        nodeInfoLbl.setBounds(0, 0, 100,180);
        nodeInfoLbl.setOpaque(true);
        nodeInfoLbl.setVisible(false);
        nodeInfoLbl.setVerticalAlignment(JLabel.TOP);
        nodeInfoLbl.setVerticalTextPosition(JLabel.TOP);
        nodeInfoLbl.setBorder(new Tools.RoundBorder(Tools.UI_BORDER_COLOR_STANDARD, Tools.PLAIN_STROKE, 10));
        
        int innerWidth  = 90;
        int innerHeight = 30;
        informBtn.setBounds(
                nodeInfoLbl.getWidth()/2 - innerWidth/2,
                nodeInfoLbl.getHeight() - innerHeight - 5,
                innerWidth,
                innerHeight);
        informBtn.setOpaque(true);
        informBtn.setBackground(nodeInfoLbl.getBackground());
        informBtn.addActionListener(a -> {
            if (!AlgorithmController.PAUSE.get()) return;
            boolean isInform = informBtn.getText().equalsIgnoreCase("inform");
            rightClickedNode.getState().setState(isInform ? 1 : 0);
            informBtn.setText(isInform ? "Uninform" : "Inform");
            if (isInform) this.graph.signalNewInformedNode();
            else          this.graph.signalNewUninformedNode();
        });
        nodeInfoLbl.add(informBtn);
        
        deleteNodeBtn.setBounds(
                nodeInfoLbl.getWidth()/2 - innerWidth/2,
                nodeInfoLbl.getHeight() - 2*(innerHeight + 5),
                innerWidth,
                innerHeight);
        deleteNodeBtn.setOpaque(true);
        deleteNodeBtn.setBackground(nodeInfoLbl.getBackground());
        deleteNodeBtn.addActionListener(a -> {
            if (!AlgorithmController.PAUSE.get()) return;
            
            Node nodeToDelete = rightClickedNode;
            graph.deleteNode(nodeToDelete);
            nodeInfoLbl.setVisible(false);
        });
        nodeInfoLbl.add(deleteNodeBtn);
        panel.add(nodeInfoLbl);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        mouse.setLocation(e.getPoint().x, e.getPoint().y);
        
        selectedItem1 = getHoveredOverNode();
        nodeInfoLbl.setVisible(false);
        
        // moving single node
        if (selectedItem1 != null) {
            dx = (mouse.getX() - selectedItem1.x);
            dy = (mouse.getY() - selectedItem1.y);
            return;
        }
        
        // panning
        try {
            XFormedPoint = panel.atx.inverseTransform(e.getPoint(), null);
        }
        catch (NoninvertibleTransformException te) {
            te.printStackTrace();
        }
        referenceX = XFormedPoint.getX();
        referenceY = XFormedPoint.getY();
        initialTransform = panel.atx;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        mouse.setLocation(e.getPoint().x, e.getPoint().y);
        
        selectedItem2 = null;
        panel.stopDrawingPotentialEdge();
        
        // RIGHT_CLICK
        // Don't drag on right click.
        if (SwingUtilities.isRightMouseButton(e)) return;
        
        // move node if one is selected
        if (selectedItem1 != null) {
            selectedItem1.moveTo((int)(mouse.getX() - dx), (int)(mouse.getY() - dy));
            return;
        }
        
        // panning
        try {
            XFormedPoint = initialTransform.inverseTransform(e.getPoint(), null);
        }
        catch (NoninvertibleTransformException te) {
            te.printStackTrace();
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
        
        // TODO: resizing somewhat working
        if (rightClickedNode != null) {
            nodeInfoLbl.setBounds(
                    (int) (rightClickedNode.ts.getBounds().getLocation().getX() + rightClickedNode.ts.getBounds().getWidth() + 10 * scale),
                    (int) (rightClickedNode.ts.getBounds().getLocation().getY()),
                    nodeInfoLbl.getBounds().width,
                    nodeInfoLbl.getBounds().height);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        nodeInfoLbl.setVisible(false);
        
        // RIGHT_CLICK
        if (e.getButton() == MouseEvent.BUTTON3) {
            selectedItem2 = null;
            panel.stopDrawingPotentialEdge();
            
            rightClickedNode = getHoveredOverNode();
            if (rightClickedNode == null) return;
            
            nodeInfoLbl.setBounds(
                    (int)(rightClickedNode.ts.getBounds().getLocation().getX() + rightClickedNode.ts.getBounds().getWidth() + 10 * scale),
                    (int)(rightClickedNode.ts.getBounds().getLocation().getY()),
                    nodeInfoLbl.getBounds().width,
                    nodeInfoLbl.getBounds().height);
            nodeInfoLbl.setVisible(true);
            nodeInfoLbl.setText(
                    "<html>" +
                            "&nbsp; Node id=" + rightClickedNode.getId() + "<br>" +
                            "&nbsp; State=" + rightClickedNode.getState().getState() +
                    "</html>");
            informBtn.setText(rightClickedNode.getState().getState() == 0 ? "Inform" : "Uninform");
            return;
        }
        
        
        // LEFT CLICK
        // connect two nodes
        if (selectedItem2 != null) {
            Node n = getHoveredOverNode();
            if (n != null) {
                graph.addEdge(n, selectedItem2);
            }
            panel.stopDrawingPotentialEdge();
            selectedItem2 = null;
            return;
        }
        // see what node was clicked, if any
        Node n =  getHoveredOverNode();
        if (n != null) {
            selectedItem2 = n;
            panel.startDrawingPotentialEdge(selectedItem2);
        } else {
            selectedItem2 = null;
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        //selectedItem2 = null;
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
