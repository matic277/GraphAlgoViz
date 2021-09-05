package impl;

import java.awt.*;
import java.awt.event.*;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.camera.Camera;

import javax.swing.*;

public class GraphExplore {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        new GraphExplore();
    }
    
    public GraphExplore() {
        Graph graph = new SingleGraph("tutorial 1");
        graph.setAttribute("ui.stylesheet", styleSheet);
        graph.setAutoCreate(true);
        graph.setStrict(false);
        Viewer viewer = graph.display(false);
        
        
        
        ViewPanel view = (ViewPanel) viewer.getDefaultView(); // ViewPanel is the view for gs-ui-swing
        view.setForeground(Color.CYAN);
        view.enableMouseOptions();
        view.resizeFrame(800, 600);
        Point m = new Point(0, 0);
    
        //final Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        //viewer.enableAutoLayout();
        //final View view = viewer.addDefaultView(false);
        //view.getCamera().setViewPercent(1);
        //((Component) view).addMouseWheelListener(new MouseWheelListener() {
        //    @Override
        //    public void mouseWheelMoved(MouseWheelEvent e) {
        //        e.consume();
        //        int i = e.getWheelRotation();
        //        double factor = Math.pow(1.25, i);
        //        Camera cam = view.getCamera();
        //        double zoom = cam.getViewPercent() * factor;
        //        Point2 pxCenter  = cam.transformGuToPx(cam.getViewCenter().x, cam.getViewCenter().y, 0);
        //        Point3 guClicked = cam.transformPxToGu(e.getX(), e.getY());
        //        double newRatioPx2Gu = cam.getMetrics().ratioPx2Gu/factor;
        //        double x = guClicked.x + (pxCenter.x - e.getX())/newRatioPx2Gu;
        //        double y = guClicked.y - (pxCenter.y - e.getY())/newRatioPx2Gu;
        //        cam.setViewCenter(x, y, 0);
        //        cam.setViewPercent(zoom);
        //    }
        //});
    
        JPanel p = new JPanel();
        //p.setLayout(null);
        p.add(new JLabel("TEST"));
        p.setBounds(new Rectangle(50, 50, 150, 150));
        p.setOpaque(true);
        p.setVisible(true);
        p.setBackground(Color.red);
        view.add(p);
        
        
        var vars = new Object() {
            final Point press = new Point(0, 0);
            final Point release = new Point(0, 0);
            double dx; double dy;
            boolean pressed;
        };
        
        view.addMouseMotionListener(new MouseMotionListener() {
            Point old = new Point(0, 0);
            @Override public void mouseMoved(MouseEvent e) {
                m.setLocation(e.getPoint());
                //view.getGraphics().drawString(e.getPoint().toString(), (int)m.x, (int)m.y);
            }
            @Override public void mouseDragged(MouseEvent e) {
                m.setLocation(e.getPoint());
                Point rec = e.getPoint();
                
                if (old.x == 0 && old.y == 0) {
                    old.setLocation(rec);
                    return;
                }
                
                double dx = rec.x - old.x;
                double dy = rec.y - old.y;
                
                System.out.println(vars.dx + ", " + vars.dy);
                
                Point3 currCntr = view.getCamera().getViewCenter();
                currCntr.x -= dx;
                currCntr.y -= dy;
                view.getCamera().setViewCenter(currCntr.x, currCntr.y, 0);
                
                old.setLocation(rec);
            }
        });
        view.addMouseListener(new MouseListener() {
            int x = 10;
            
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clicked");
                m.setLocation(e.getPoint());
                //m.setLocation(e.getLocationOnScreen());
                graph.addNode(x+"");
                graph.getNode(x+"").setAttribute("xy", m.x, m.y);
                x++;
            }
            @Override public void mousePressed(MouseEvent e) {
                m.setLocation(e.getPoint());
                vars.press.setLocation(e.getPoint());
                vars.pressed = true;
    
                vars.dx = vars.press.x - m.x;
                vars.dy = vars.press.y - vars.release.y;
            }
            @Override public void mouseReleased(MouseEvent e) {
                vars.release.setLocation(e.getPoint());
                vars.pressed = false;
                //
                //Point3 currCntr = view.getCamera().getViewCenter();
                //currCntr.x -= dx;
                //currCntr.y -= dy;
                //view.getCamera().setViewCenter(currCntr.x, currCntr.y, 0);
            }
            @Override public void mouseEntered(MouseEvent e) { }
            @Override public void mouseExited(MouseEvent e) { }
        });
        double[] scale = new double[]{1};
        view.addMouseWheelListener(e -> {
            //scale[0] -= (0.05 * e.getWheelRotation());
            //scale[0] =  Math.max(0.1, scale[0]);
            //view.getCamera().setViewCenter(m.x, m.y, 0);
            //view.getCamera().setViewPercent(scale[0]);
            e.consume();
            int i = e.getWheelRotation();
            double factor = Math.pow(1.25, i);
            Camera cam = view.getCamera();
            double zoom = cam.getViewPercent() * factor;
            Point2 pxCenter  = cam.transformGuToPx(cam.getViewCenter().x, cam.getViewCenter().y, 0);
            Point3 guClicked = cam.transformPxToGu(e.getX(), e.getY());
            double newRatioPx2Gu = cam.getMetrics().ratioPx2Gu/factor;
            double x = guClicked.x + (pxCenter.x - e.getX())/newRatioPx2Gu;
            double y = guClicked.y - (pxCenter.y - e.getY())/newRatioPx2Gu;
            cam.setViewCenter(x, y, 0);
            cam.setViewPercent(zoom);
        });
//        view.getCamera().setViewCenter(200, 200, 0);
//        view.getCamera().setViewPercent(0.5);
        
        
        
        graph.addNode("0");
        graph.addNode("1");
        graph.addNode("2");
        graph.addNode("3");
        graph.addNode("4");
        graph.addNode("5");
        
        graph.addEdge("00", "0", "1");
        graph.addEdge("01", "1", "2");
        graph.addEdge("02", "2", "0");
    
        graph.addEdge("X", "2", "3");
        graph.getEdge("X").setAttribute("ui.label", "XXXXXXXXX");
        
        graph.addEdge("10", "3", "4");
        graph.addEdge("11", "4", "5");
        graph.addEdge("12", "5", "3");
    
        graph.getNode("0").setAttribute("xy", 100, 100);
        graph.getNode("1").setAttribute("xy", 100, 300);
        graph.getNode("2").setAttribute("xy", 200, 200);
        
        graph.getNode("3").setAttribute("xy", 300, 200);
        graph.getNode("4").setAttribute("xy", 400, 100);
        graph.getNode("5").setAttribute("xy", 400, 300);
        
        graph.getNode("0").setAttribute("ui.label", "NODE 0");
        
        System.out.println(view.getCamera().getViewCenter());
        
        //graph.addEdge("AB", "A", "B");
        //graph.addEdge("BC", "B", "C");
        //graph.addEdge("CA", "C", "A");
        //graph.addEdge("AD", "A", "D");
        //graph.addEdge("DE", "D", "E");
        //graph.addEdge("DF", "D", "F");
        //graph.addEdge("EF", "E", "F");
        
        
        //new Thread(()-> {
        //    while (true) {
        //        sleep(250);
        //        Point3 p = view.getCamera().getViewCenter();
        //        view.getCamera().setViewCenter(p.x+10, p.y, p.z);
        //    }
        //}).start();
    }
    
    //public void explore(Node source) {
    //    Iterator<? extends Node> k = source.getBreadthFirstIterator();
    //
    //    while (k.hasNext()) {
    //        Node next = k.next();
    //        next.setAttribute("ui.class", "marked");
    //        sleep();
    //    }
    //}
    
    protected void sleep(long t) { try { Thread.sleep(t); } catch (Exception e) {} }
    
    protected String styleSheet =
            "node {" +
                    "	fill-color: black;" +
                    "}" +
                    "node.marked {" +
                    "	fill-color: red;" +
                    "}";
}