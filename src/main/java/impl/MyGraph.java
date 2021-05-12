package impl;

import core.ComponentDrawer;
import core.Drawable;
import core.GraphChangeObserver;
import core.GraphObservable;
import impl.tools.Edge;
import impl.tools.Tools;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.awt.*;
import java.awt.List;
import java.awt.geom.AffineTransform;
import java.util.*;

public class MyGraph implements Drawable, GraphObservable {
    
    public int numOfNodes = 0;
    static private int nextId = -1;
    
    public int informedNodes = 0;
    
    Graph<Node, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
    
    private ComponentDrawer edgeDrawer = ComponentDrawer.getEdgeDrawer(graph);
    
    // singleton
    private static final MyGraph instance = new MyGraph();
    public static MyGraph getInstance() { return instance; }
    private MyGraph() { }
    
    public void clearGraph() {
        nextId = -1;
        numOfNodes = 0;
        
        // These collections are unmodifiable!
        //  graph.edgeSet().clear();
        //  graph.vertexSet().clear();
        
        // Has its own problems with edge drawing (potentially elsewhere as-well)
        // graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        
        // Does not work (concurrent exception on library's part)
        // this.graph.removeAllVertices(this.graph.vertexSet());
        
        // Slow but works (creates copy references for every edge and node...)
        // (http://jgrapht-users.107614.n3.nabble.com/remove-all-edges-and-vertices-td4024747.html)
        LinkedList<DefaultEdge> copy = new LinkedList<>(graph.edgeSet());
        graph.removeAllEdges(copy);
        LinkedList<Node> copy2 = new LinkedList<>(graph.vertexSet());
        graph.removeAllVertices(copy2);
    
        observers.forEach(GraphChangeObserver::onGraphClear);
    }
    
    // TODO: increasing nextId on getNode call, not node is not necessarily added to graph
    @SuppressWarnings("deprecation")
    public static Node getNode() {
        return new Node(-nextId, nextId, ++nextId);
    }
    
    @Override
    public void draw(Graphics2D g, AffineTransform at) {
        g.setColor(Color.BLACK);
        
        g.setStroke(Tools.BOLD_STROKE);
        edgeDrawer.draw(g, at, null);
        
        this.graph.vertexSet().forEach(n -> n.draw(g, at));
    }
    
    // bad... slow
    public boolean connectById(int n1Id, int n2Id) {
        Optional<Node> n1 = this.graph.vertexSet().stream().filter(n -> n.id == n1Id).findFirst();
        Optional<Node> n2 = this.graph.vertexSet().stream().filter(n -> n.id == n2Id).findFirst();
        
        if (n1.isPresent() && n2.isPresent()) {
            return addEdge(n1.get(), n2.get());
        } else {
            throw new RuntimeException("Nodes not found by id: " + n1Id + ", " + n2Id + ".");
        }
    }
    
    public void deleteNode(Node node) {
        boolean removed = this.graph.removeVertex(node);
        System.out.println("REMOVING NODE="+node+"="+removed);
        observers.forEach(GraphChangeObserver::onNodeDeleted);
    }
    
    public void addNode(Node n) {
        this.graph.addVertex(n);
        observers.forEach(GraphChangeObserver::onNodeAdded);
        numOfNodes++;
    }
    
    public boolean addEdge(Node n1, Node n2) {
        DefaultEdge e = this.graph.addEdge(n1, n2);
        System.out.println("ADDED EDGE: " + e);
        observers.forEach(GraphChangeObserver::onEdgeAdded);
        return e != null;
    }
    
//    public boolean containsEdge(Node n1, Node n2) {
//        return this.edges.contains(new Edge(n1, n2));
//    }
    public Set<Node> getNodes() { return this.graph.vertexSet(); }
    
//    public Set<Edge> getEdges() { return this.edges; }
    
    public void drawEdges(boolean draw) {
        this.edgeDrawer = draw ?
                ComponentDrawer.getEdgeDrawer(this.graph) : ComponentDrawer.getNullDrawer();
    }
    
    public Node getNodeById(int id) {
        Optional<Node> node = this.graph.vertexSet().stream().filter(n -> n.id == id).findFirst();
        if (node.isPresent()) return node.get();
        throw new RuntimeException("Can't find node by id=" + id + ".");
    }
    
    public Graph<Node, DefaultEdge> getGraph() { return this.graph; }
    
    Set<GraphChangeObserver> observers = new HashSet<>(5);
    
    @Override
    public void addObserver(GraphChangeObserver observer) {
        this.observers.add(observer);
    }
    
    @Override
    public void removeObserver(GraphChangeObserver observer) {
        this.observers.remove(observer);
    }
}
