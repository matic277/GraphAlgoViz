package impl.panels.simulationPanels;

import core.GraphChangeObserver;
import impl.*;
import impl.listeners.SimulationPanelListener;
import impl.tools.Tools;
import impl.windows.SimulationWindow;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationPanel extends JPanel implements GraphChangeObserver {
    
    TopPanel parent;
    MyGraph graph;
    
    private final Point2D mouse;
    public AffineTransform atx = new AffineTransform();
    SimulationPanelListener listener;
    
    private final Color BG_COLOR = new Color(45, 45, 45);
    private final Color INFO_BG_COLOR = UIManager.getColor("Panel.background");
    
    // potential edge drawing
    Node edgeSourceNode;
    
    NumberFormat formatter = new DecimalFormat(); { formatter.setMaximumFractionDigits(2); }
    
    // FPS measuring vars
    private final int FPS = 1000 / 144;
    private AtomicInteger fpsCounter = new AtomicInteger(0);
    private int currentFps = 0;
    
    public SimulationPanel(TopPanel parent) {
        this.parent = parent;
        this.graph = MyGraph.getInstance();
        
        this.setLayout(null);
        this.setOpaque(true);
        this.setVisible(true);
        
        listener = new SimulationPanelListener(this);
        this.addMouseMotionListener(listener);
        this.addMouseListener(listener);
        this.addMouseWheelListener(listener);
        this.mouse = listener.getMouse();
        
        new Thread(() -> {
            while (true) {
                Tools.sleep(1000);
                currentFps = fpsCounter.intValue();
                fpsCounter.set(0);
            }
        }).start();
        
        
        // inner is standard, but outer is the same as background,
        // otherwise there is a small space between inner border and outer window
        
        //  fill this area
        //     ˇ
        // | panel | panel.... .... panel | panel |
        // ^                              ^
        // window border                inner border
        
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1,1,1,1, this.getBackground()),
                BorderFactory.createMatteBorder(1,1,1,1, Tools.UI_BORDER_COLOR_STANDARD)));
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
        
        gr.setColor(BG_COLOR);
        gr.fillRect(0, 0, getWidth(), getHeight());
        
        drawComponents(gr);
        
        gr.setFont(Tools.getMonospacedFont(12));
        
        // mouse + lines
        gr.setColor(Color.BLACK);
        gr.drawLine(0, (int)mouse.getY(), getWidth(), (int)mouse.getY());
        gr.drawLine((int)mouse.getX(), 0, (int)mouse.getX(), getHeight());
        // mouse
        gr.setColor(Color.red);
        gr.fillRect((int)mouse.getX()-2, (int)mouse.getY()-2, 4, 4);
        gr.setColor(Color.BLACK);
        gr.drawString("["+(int)mouse.getX()+","+(int)mouse.getY()+"]", (int)mouse.getX() + 3, (int)mouse.getY()-6);
        
        gr.setColor(INFO_BG_COLOR);
        gr.fillRoundRect(getWidth()-151, 5, 145, 110, 10, 10);
        gr.setColor(Tools.UI_BORDER_COLOR_STANDARD);
        gr.drawRoundRect(getWidth()-151, 5, 145, 110, 10, 10);
        gr.setColor(Color.white);
        gr.drawString("status: " + (AlgorithmController.PAUSE.get() && !AlgorithmController.NEXT_ROUND_BUTTON_PRESSED.get() ? "PAUSED" : "RUNNING"),
                getWidth()-140,
                25);
        gr.drawString("scale:  " + formatter.format(listener.scale),
                getWidth()-140,
                45);
        gr.drawString("state:  " + AlgorithmController.currentStateIndex,
                getWidth()-140,
                65);
        gr.drawString("states: " + AlgorithmController.totalStates,
                getWidth()-140,
                85);
        gr.drawString("FPS:    " + currentFps,
                getWidth()-140,
                105);
        
        fpsCounter.incrementAndGet();
        long t1 = System.currentTimeMillis() - t0;
        if (t1 < FPS) {
            try { Thread.sleep(FPS - t1); }
            catch (Exception e) { e.printStackTrace(); }
        }
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
    
    @Override
    public void onGraphClear() {
    
    }
    
    @Override
    public void onGraphImport() {
        this.repaint();
    }
    
    @Override public void onNewInformedNode() {}
    @Override public void onNewUninformedNode() {}
    @Override public void edgeAdded(GraphEdgeChangeEvent<Node, DefaultEdge> e) {}
    @Override public void edgeRemoved(GraphEdgeChangeEvent<Node, DefaultEdge> e) {}
    @Override public void vertexAdded(GraphVertexChangeEvent<Node> e) {}
    @Override public void vertexRemoved(GraphVertexChangeEvent<Node> e) {}
}
