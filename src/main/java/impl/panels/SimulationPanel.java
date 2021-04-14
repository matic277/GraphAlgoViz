package impl.panels;

import core.Algorithm;
import core.Selectable;
import impl.AlgorithmController;
import impl.Graph;
import impl.Node;
import impl.listeners.SimulationPanelListener;
import impl.tools.Tools;
import impl.tools.Vector;
import impl.windows.SimulationWindow;
import org.graphstream.graph.Edge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.*;

public class SimulationPanel extends JPanel {
    
    SimulationWindow parent;
    
    Graph graph;
    
    int zoomLevel = 100;
    boolean zoom;
    double zoomFactor = 1;
    
    Point2D mouse = new Point2D.Double();
    AffineTransform atx = new AffineTransform();
    
    // potential edge drawing
    Node edgeSourceNode;
    

    public SimulationPanel(SimulationWindow parent, Graph g, Dimension panelSize) {
        this.parent = parent;
        this.graph = g;
        
        this.setSize(panelSize);
        this.setPreferredSize(panelSize);
        this.setLayout(null);
        this.setOpaque(true);
        this.setVisible(true);
        this.setBackground(Color.red);
    
        var l = new SimulationPanelListener(this);
        this.addMouseMotionListener(l);
        this.addMouseListener(l);
        this.addMouseWheelListener(l);
        
//        JLabel ly = new JLabel("TEST");
//        ly.setOpaque(true);
//        ly.setBackground(Color.red);
//        ly.setBounds(50, 50, 50, 50);
//        this.add(ly);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // anti-aliasing
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        gr.setColor(Color.PINK);
        gr.fillRect(0, 0, getWidth(), getHeight());
        
        gr.transform(atx);
        
        // mouse
        gr.setColor(Color.red);
        gr.fillRect((int)mouse.getX()-2, (int)mouse.getY()-2, 4, 4);
        gr.setColor(Color.BLACK);
        g.drawString("["+(int)mouse.getX()+","+(int)mouse.getY()+"]", (int)mouse.getX() + 3, (int)mouse.getY()-6);
        
        // mouse + lines
        gr.setColor(Color.BLACK);
        gr.drawLine(0, (int)mouse.getY(), getWidth(), (int)mouse.getY());
        gr.drawLine((int)mouse.getX(), 0, (int)mouse.getX(), getHeight());
        
        gr.setColor(Color.RED);
        gr.drawRect(3, 3, getWidth()-6, getHeight()-6);
        
        
        // bad zooming
        // mouse de-sync and speed problems
        // https://stackoverflow.com/questions/6543453/zooming-in-and-zooming-out-within-a-panel
        {
//            var affine = new AffineTransform();
////            affine.translate(mouse.getX()/2, mouse.getY()/2);
//            affine.translate(getWidth()/2, getHeight()/2);
//            affine.scale(zoomFactor, zoomFactor);
////            affine.translate(-mouse.getX()/2, -mouse.getY()/2);
//            affine.translate(-getWidth()/2, -getHeight()/2);
//            gr.transform(affine);
        }
        // draw components after affine transformations!
        
        
        drawComponents(gr);
        
        Tools.sleep(1000/144);
        super.repaint();
    }
    
    public void startDrawingPotentialEdge(Node sourceNode) {
        this.edgeSourceNode = sourceNode;
    }
    
    public void stopDrawingPotentialEdge() {
        this.edgeSourceNode = null;
    }
    
    public void drawComponents(Graphics2D g) {
        graph.draw(g);
        
        if (edgeSourceNode != null) {
            Stroke oldStr = g.getStroke();
            g.setStroke(new BasicStroke(2));
            g.setColor(Color.BLACK);
            g.drawLine(edgeSourceNode.x, edgeSourceNode.y, (int)mouse.getX(), (int)mouse.getY());
            g.setStroke(oldStr);
        }
    }
    
    public void setGraph(Graph g) {
        this.graph = g;
    }
    
    // TODO: fix thread safety
    public Graph getGraph() {
        return this.graph;
    }
    
    public Point2D getMouse() {
        return this.mouse;
    }
    
    public AffineTransform getAffineTransformation() {
        return this.atx;
    }
}
