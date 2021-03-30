package impl.panels;

import core.Selectable;
import impl.Node;
import impl.tools.Pair;
import impl.windows.SimulationWindow;
import org.graphstream.graph.Edge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimulationPanel extends JPanel {
    
    SimulationWindow parent;
    
    // TODO: move graph elts to Environment
    Set<Node> nodes;
    Set<Pair<Node>> edges;
    
    Map<Node, Edge> graph;
    
    
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
        Node n3 = new Node(200, 250, 3);
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        edges.add(new Pair<>(n1, n2));
        edges.add(new Pair<>(n2, n3));
        
        graph = new HashMap<>();
        
        
        Listener l = new Listener();
        this.addMouseMotionListener(l);
        this.addMouseListener(l);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // anti-aliasing
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        gr.setColor(Color.PINK);
        gr.fillRect(0, 0, getWidth(), getHeight());
        
        drawComponents(gr);
        
        // mouse
        g.setColor(Color.red);
        g.fillRect((int)mouse.getX()-2, (int)mouse.getY()-2, 4, 4);
    
        // + lines
        g.setColor(Color.BLACK);
        g.drawLine(0, (int)SimulationPanel.mouse.getY(), getWidth(), (int)SimulationPanel.mouse.getY());
        g.drawLine((int)SimulationPanel.mouse.getX(), 0, (int)SimulationPanel.mouse.getX(), getHeight());
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
    
    class Listener implements MouseListener, MouseMotionListener {
        
        boolean dragging = false;
        Double dx, dy;
        Selectable selectedItem;
        
        @Override
        public void mouseClicked(MouseEvent e) {
        }
        
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
        public void mouseEntered(MouseEvent e) {
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
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
                SimulationPanel.this.repaint();
                return;
            }
            
//            boolean someNodeWasSelected = false;
//            for (Node n : SimulationPanel.this.nodes) {
//                if (n.isSelected(e.getPoint())) {
//                    someNodeWasSelected = true;
//                    if (dx == null) dx = e.getPoint().getX() - n.x;
//                    if (dy == null) dy = e.getPoint().getY() - n.y;
//                    n.moveTo(new Point2D.Double(
//                            e.getPoint().getX() - dx,
//                            e.getPoint().getY() - dy));
//                    SimulationPanel.this.repaint();
//                    break;
//                }
//            }
//            if (someNodeWasSelected) return;
            
            // nothing was selected, so drag all elements
            for (Node n : SimulationPanel.this.nodes) {
                Point2D itemLocation = n.getLocation();
                if (dx == null) dx = e.getPoint().getX() - itemLocation.getX();
                if (dy == null) dy = e.getPoint().getY() - itemLocation.getY();
                n.moveTo(new Point2D.Double(
                        e.getPoint().getX() - n.dx,
                        e.getPoint().getY() - n.dy));
                SimulationPanel.this.repaint();
            }
        }
        
        @Override
        public void mouseMoved(MouseEvent e) {
//            System.out.println("mov");
            mouse.setLocation(e.getPoint());
        }
    }
}
