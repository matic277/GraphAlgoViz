package impl.panels;

import core.Observer;
import core.StateObserver;
import impl.*;
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
    
    TopPanel parent;
    MyGraph graph;
    
    private final Point2D mouse;
    public AffineTransform atx = new AffineTransform();
    SimulationPanelListener listener;
    
    // potential edge drawing
    Node edgeSourceNode;
    
    final long FPS = 1000L / 144L; // 144FPS
    
    NumberFormat formatter = new DecimalFormat(); { formatter.setMaximumFractionDigits(2); }
    
    public SimulationPanel(TopPanel parent) {
        this.parent = parent;
        this.graph = MyGraph.getInstance();
        
        this.setLayout(null);
        this.setOpaque(true);
        this.setVisible(true);
        this.setBackground(Color.red);
        
        listener = new SimulationPanelListener(this);
        this.addMouseMotionListener(listener);
        this.addMouseListener(listener);
        this.addMouseWheelListener(listener);
        this.mouse = listener.getMouse();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        long t0 = System.currentTimeMillis();
        
        // removes artifacts when JSplitPanes resize
        super.paintComponent(g);
        
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
        
        g.setFont(Tools.getFont(14));
        gr.drawString("status: " + (AlgorithmController.PAUSE.get() && !AlgorithmController.NEXT_ROUND_BUTTON_PRESSED.get() ? "PAUSED" : "RUNNING"),
                getWidth()-120,
                20);
        gr.drawString("scale:  " + formatter.format(listener.scale),
                getWidth()-120,
                35);
        gr.drawString("state:  " + AlgorithmController.currentStateIndex,
                getWidth()-120,
                60);
        gr.drawString("states: " + AlgorithmController.totalStates,
                getWidth()-120,
                75);
        
        drawComponents(gr);
        
        long td = System.currentTimeMillis() - t0;
        super.repaint(td > FPS ? 0 : td);
    }
    
    public void startDrawingPotentialEdge(Node sourceNode) {
        this.edgeSourceNode = sourceNode;
    }
    
    public void stopDrawingPotentialEdge() {
        this.edgeSourceNode = null;
    }
    
    public void drawComponents(Graphics2D g) {
        graph.draw(g, atx);
        
        if (edgeSourceNode != null) {
            g.setStroke(Tools.BOLD_STROKE);
            g.setColor(Color.BLACK);
            g.drawLine(
                    (int)edgeSourceNode.ts.getBounds().getCenterX(),
                    (int)edgeSourceNode.ts.getBounds().getCenterY(),
                    (int)mouse.getX(),
                    (int)mouse.getY());
            g.setStroke(Tools.PLAIN_STROKE);
        }
    }
    
    public void setGraph(MyGraph g) { this.graph = g; }
    
    public MyGraph getGraph() { return this.graph; }
    
    public Point2D getMouse() { return this.mouse; }
    
    public AffineTransform getAffineTransformation() { return this.atx; }
    
    public SimulationPanelListener getPanelListener() { return this.listener; }
    
    public SimulationWindow getSimulationWindow() { return this.parent.getSimulationWindow(); }
    
    public void onNewGraphImport() {
        this.listener.onNewGraphImport();
    }
}
