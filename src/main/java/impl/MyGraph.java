package impl;

import core.ComponentDrawer;
import core.Drawable;
import core.GraphChangeObserver;
import core.GraphObservable;
import impl.tools.Edge;
import impl.tools.Tools;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphListener;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MyGraph implements Drawable, GraphObservable {
    
    public int numOfNodes = 0;
    private static int nextId = -1;
    
    public AtomicInteger informedNodes = new AtomicInteger(0);
    
    ListenableGraph<Node, DefaultEdge> graph = new DefaultListenableGraph<>(new DefaultUndirectedGraph<>(DefaultEdge.class));
    
    private ComponentDrawer edgeDrawer = ComponentDrawer.getNullDrawer();
    
    public static final Object LOCK = new Object();
    
    // singleton
    private static final MyGraph instance = new MyGraph();
    public static MyGraph getInstance() { return instance; }
    private MyGraph() { setListener(); }
    
    // TODO: problematic:
    // gets called when clearing graph, for each node, this is a pretty big problem
    // should probably remove these methods/listeners and just do it by hand with the custom
    // written listeners in THIS class.
    // OR
    // remove the listener before clearing the graph,
    // then re-add it after the graph is clear!
    public void setListener() {
        graph.addGraphListener(new GraphListener<>() {
            @Override public void edgeRemoved(GraphEdgeChangeEvent<Node, DefaultEdge> graphEdgeChangeEvent) { }
            @Override public void edgeAdded(GraphEdgeChangeEvent<Node, DefaultEdge> graphEdgeChangeEvent) {
                observers.forEach(GraphChangeObserver::onEdgeAdded);
            }
            @Override public void vertexAdded(GraphVertexChangeEvent<Node> graphVertexChangeEvent) {
                observers.forEach(GraphChangeObserver::onNodeDeleted);
            }
            @Override public void vertexRemoved(GraphVertexChangeEvent<Node> graphVertexChangeEvent) {
                observers.forEach(GraphChangeObserver::onNodeDeleted);
            }
        });
    }
    
    public void clearGraph() { synchronized (LOCK) {
        nextId = -1;
        numOfNodes = 0;
        
        // These collections are unmodifiable!
        //  graph.edgeSet().clear();
        //  graph.vertexSet().clear();
        
        // Has its own problems with edge drawing (potentially elsewhere as-well)
//         graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        
        // Does not work (concurrent exception on library's part)
        // this.graph.removeAllVertices(this.graph.vertexSet());
        
        // Slow but works (creates copy references for every edge and node...)
        // (http://jgrapht-users.107614.n3.nabble.com/remove-all-edges-and-vertices-td4024747.html)
        LinkedList<DefaultEdge> copy = new LinkedList<>(graph.edgeSet());
        graph.removeAllEdges(copy);
        LinkedList<Node> copy2 = new LinkedList<>(graph.vertexSet());
        graph.removeAllVertices(copy2);
        
        observers.forEach(GraphChangeObserver::onGraphClear);
    }}
    
    public void onInformedNodesChange() {
        observers.forEach(GraphChangeObserver::onNewInformedNode);
    }
    
    public synchronized void signalNewInformedNode() {
        this.informedNodes.incrementAndGet();
        onInformedNodesChange();
    }
    
    public void signalNewUninformedNode() {
        this.informedNodes.decrementAndGet();
        onInformedNodesChange();
    }
    
    
    // TODO: increasing nextId on getNode call, not node is not necessarily added to graph
    @SuppressWarnings("deprecation")
    public static Node getNode() {
        return new Node(-nextId, nextId, ++nextId);
    }
    
    @Override
    public void draw(Graphics2D g, AffineTransform at) { synchronized (LOCK) {
        g.setColor(Color.BLACK);
        
        // draw edges
        g.setStroke(Tools.BOLD_STROKE);
        g.setColor(new Color(Edge.EDGE_COLOR.getRed(), Edge.EDGE_COLOR.getGreen(), Edge.EDGE_COLOR.getBlue(), Edge.opacity));
        edgeDrawer.draw(g, at, null);
        
        // draw nodes
        this.graph.vertexSet().forEach(n -> n.draw(g, at));
    }}
    
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
    
    public void deleteNode(Node node) { synchronized (LOCK) {
        boolean removed = this.graph.removeVertex(node);
        System.out.println("REMOVING NODE="+node+"="+removed);
        observers.forEach(GraphChangeObserver::onNodeDeleted);
    }}
    
    public void addNode(Node n) { synchronized (LOCK) {
        this.graph.addVertex(n);
        observers.forEach(GraphChangeObserver::onNodeAdded);
        numOfNodes++;
    }}
    
    public boolean addEdge(Node n1, Node n2) {  synchronized (LOCK) {
        DefaultEdge e = this.graph.addEdge(n1, n2);
        System.out.println("ADDED EDGE: " + e);
        observers.forEach(GraphChangeObserver::onEdgeAdded);
        return e != null;
    }}
    
//    public boolean containsEdge(Node n1, Node n2) {
//        return this.edges.contains(new Edge(n1, n2));
//    }
    public Set<Node> getNodes() { return this.graph.vertexSet(); }
    
//    public Set<Edge> getEdges() { return this.edges; }
    
    public void drawEdges(boolean draw) { synchronized (LOCK) {
        this.edgeDrawer = draw ?
                ComponentDrawer.getEdgeDrawer() : ComponentDrawer.getNullDrawer();
    }}
    
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
    
    public void setNumberOfInformedNodes(int num) { this.informedNodes.set(num); this.onInformedNodesChange(); }
    
    public int getNumberOfInformedNodes() { return this.informedNodes.get(); }
}
