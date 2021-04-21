package impl.panels;

import core.Algorithm;
import core.Selectable;
import impl.AlgorithmController;
import impl.Graph;
import impl.Node;
import impl.listeners.SimulationPanelListener;
import impl.tools.Tools;
import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SimulationPanel extends JPanel {
    
    SimulationWindow parent;
    
    Graph graph;
    
    private final Point2D mouse;
    public AffineTransform atx = new AffineTransform();
    SimulationPanelListener listener;
    
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
    
        listener = new SimulationPanelListener(this);
        this.addMouseMotionListener(listener);
        this.addMouseListener(listener);
        this.addMouseWheelListener(listener);
        
        mouse = listener.getMouse();
        
//        JLabel ly = new JLabel("TEST");
//        ly.setOpaque(true);
//        ly.setBackground(Color.red);
//        ly.setBounds(50, 50, 50, 50);
//        this.add(ly);
    }
    
    NumberFormat formatter = new DecimalFormat(); {
        formatter.setMaximumFractionDigits(2);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // anti-aliasing
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        gr.setColor(Tools.bgColor);
        gr.fillRect(0, 0, getWidth(), getHeight());
        
        // mouse
        gr.setColor(Color.red);
        gr.fillRect((int)mouse.getX()-2, (int)mouse.getY()-2, 4, 4);
        gr.setColor(Color.BLACK);
        g.drawString("["+(int)mouse.getX()+","+(int)mouse.getY()+"]", (int)mouse.getX() + 3, (int)mouse.getY()-6);
        // mouse + lines
        gr.setColor(Color.BLACK);
        gr.drawLine(0, (int)mouse.getY(), getWidth(), (int)mouse.getY());
        gr.drawLine((int)mouse.getX(), 0, (int)mouse.getX(), getHeight());
        
        gr.drawString("scale: " + formatter.format(listener.scale), getWidth()-80, getHeight()-30);
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
        graph.draw(g, atx);
        
//        if (edgeSourceNode != null) {
//            g.setStroke(Tools.BOLD_STROKE);
//            g.setColor(Color.BLACK);
//            g.drawLine(edgeSourceNode.x, edgeSourceNode.y, (int)mouse.getX(), (int)mouse.getY());
//            g.setStroke(Tools.PLAIN_STROKE);
//        }
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
