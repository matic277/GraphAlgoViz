package impl.panels;

import core.Algorithm;
import core.Selectable;
import impl.AlgorithmController;
import impl.Node;
import impl.tools.Pair;
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
    
    // TODO: move graph elts to Environment
    Set<Node> nodes;
    Set<Pair<Node>> edges;
    
    Map<Node, Edge> graph; // unused
    
    int zoomLevel = 100;
    boolean zoom;
    double zoomFactor = 1;
    
    public static Point2D mouse = new Point2D.Double();
    
    public SimulationPanel(SimulationWindow parent, Dimension panelSize) {
        this.parent = parent;
        
        this.setSize(panelSize);
        this.setPreferredSize(panelSize);
        this.setLayout(null);
        this.setOpaque(true);
        this.setVisible(true);
        this.setBackground(Color.red);
        
        nodes = new HashSet<>();
        edges = new HashSet<>();
        Node n1 = new Node(100, 100, 0);
        Node n2 = new Node(101, 170, 1);
        Node n3 = new Node(150, 250, 3);
        Node n4 = new Node(270, 280, 4);
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(n4);
        edges.add(new Pair<>(n1, n2));
        edges.add(new Pair<>(n2, n3));
        edges.add(new Pair<>(n2, n4));
        
        n1.neighbors.add(n2);
        n2.neighbors.add(n1);
        
        n2.neighbors.add(n3);
        n3.neighbors.add(n2);
        
        n2.neighbors.add(n4);
        n4.neighbors.add(n2);
        
        graph = new HashMap<>();
        
//        JLabel ly = new JLabel("TEST");
//        ly.setOpaque(true);
//        ly.setBackground(Color.red);
//        ly.setBounds(50, 50, 50, 50);
//        this.add(ly);
        
        var l = new Listener();
        this.addMouseMotionListener(l);
        this.addMouseListener(l);
        this.addMouseWheelListener(l);
    
        try {
            r = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        
        n1.info = 1;
    
        AlgorithmController ac = new AlgorithmController(this, nodes);
        Thread controller = new Thread(ac);
        controller.start();
        
        
//        Thread controller = new Thread(() -> {
//            while (true) {
//                Tools.sleep(500);
//                for (Node n : nodes) {
//                    getAlgorithm().run(n);
//                }
//            }
//        });
//        controller.start();
        
        
    }
    
//    public Algorithm getAlgorithm() {
//        final Random r = new Random();
//        return (node) -> {
//            int i = r.nextInt(node.neighbors.size());
//            System.out.println(i);
//            Node randNode = node.neighbors.get(i);
//            int info = randNode.info;
//            node.info += info;
//        };
//    }
    
    AffineTransform atx = new AffineTransform();
    Robot r;
    
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
    
        // + lines
        gr.setColor(Color.BLACK);
        gr.drawLine(0, (int)SimulationPanel.mouse.getY(), getWidth(), (int)SimulationPanel.mouse.getY());
        gr.drawLine((int)SimulationPanel.mouse.getX(), 0, (int)SimulationPanel.mouse.getX(), getHeight());
    
        
        gr.drawString("Zoom: " + zoomLevel, getWidth() - 80, getHeight() - 20);
    
    
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
    
    public void drawComponents(Graphics2D g) {
        g.setColor(Color.BLACK);
        for (Pair<Node> edge : edges) {
            Node n1 = edge.getA();
            Node n2 = edge.getB();
            g.drawLine(n1.x, n1.y, n2.x, n2.y);
        }
        nodes.forEach(n -> n.draw(g));
    }
    
    public void addNode(Node node) {
        System.out.println("new node added");
        this.nodes.add(node);
    }
    
    // TODO move to separate class
    class Listener implements MouseListener, MouseMotionListener, MouseWheelListener {
        
        boolean dragging = false;
        Double dx, dy;
        Selectable selectedItem;
        
        @Override
        public void mousePressed(MouseEvent e) {
//            selectedItem = SimulationPanel.this.nodes.stream().findFirst().get();
            for (Node n : SimulationPanel.this.nodes) {
                if (n.isSelected(e.getPoint())) {
                    selectedItem = n;
                    dragging = true;
                    return;
                }
            }
            // distance to mouse for all draggables/selectables
            for (Node n : SimulationPanel.this.nodes) {
                n.dx = e.getPoint().getX() - n.x;
                n.dy = e.getPoint().getY() - n.y;
            }
        }
    
        @Override
        public void mouseReleased(MouseEvent e) {
            selectedItem = null;
            dragging = false;
            dx = null; dy = null;
            
            for (Node n : SimulationPanel.this.nodes) {
                n.dx = 0;
                n.dy = 0;
            }
        }
    
        @Override
        public void mouseDragged(MouseEvent e) {
//            System.out.println("dragging");
            mouse.setLocation(e.getPoint());
        
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
            for (Node n : SimulationPanel.this.nodes) {
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
//            // scroll down, zoom in
//            if (e.getWheelRotation() < 0) {
//                if (zoomLevel >= 150) return;
//                zoomLevel += 5;
//                zoomFactor += 0.1;
//                zoom = true;
//            }
//
//            // scroll up, zoom out
//            else {
//                if (zoomLevel <= 70) return;
//                zoomLevel -= 5;
//                zoomFactor -= 0.1;
//                zoom = true;
//            }
    
    
            // bad zooming
            // mouse de-sync and speed problems
            
            Point2D p1 = e.getPoint();
            Point2D p2 = null;
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
            
//            SimulationPanel.this.repaint();
        }
        
        @Override public void mouseMoved(MouseEvent e) {
            mouse.setLocation(e.getPoint());
            SimulationPanel.this.repaint();
        }
        @Override public void mouseEntered(MouseEvent e) { }
        @Override public void mouseClicked(MouseEvent e) { }
        @Override public void mouseExited(MouseEvent e) { }
    }
}
