import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;

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
        Viewer viewer = graph.display(true);
        viewer.newViewerPipe().addViewerListener(new ViewerListener() {
            @Override
            public void viewClosed(String s) {
            
            }
    
            @Override
            public void buttonPushed(String s) {
                System.out.println("aa");
            }
    
            @Override
            public void buttonReleased(String s) {
        
            }
    
            @Override
            public void mouseOver(String s) {
                System.out.println("aa");
            }
    
            @Override
            public void mouseLeft(String s) {
                System.out.println("aa");
            }
        });
        ViewPanel view = (ViewPanel) viewer.getDefaultView(); // ViewPanel is the view for gs-ui-swing
        view.resizeFrame(800, 600);
        view.enableMouseOptions();
//        view.getCamera().setViewCenter(200, 200, 0);
//        view.getCamera().setViewPercent(0.5);
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
        graph.addEdge("AD", "A", "D");
        graph.addEdge("DE", "D", "E");
        graph.addEdge("DF", "D", "F");
        graph.addEdge("EF", "E", "F");
        
        
        for (Node node : graph) {
//            node.setAttribute("ui.label", node.getId());
        }
        
//        explore(graph.getNode("A"));
    }
    
    public void explore(Node source) {
        Iterator<? extends Node> k = source.getBreadthFirstIterator();
        
        while (k.hasNext()) {
            Node next = k.next();
            next.setAttribute("ui.class", "marked");
            sleep();
        }
    }
    
    protected void sleep() {
        try { Thread.sleep(1000); } catch (Exception e) {}
    }
    
    protected String styleSheet =
            "node {" +
                    "	fill-color: black;" +
                    "}" +
                    "node.marked {" +
                    "	fill-color: red;" +
                    "}";
}