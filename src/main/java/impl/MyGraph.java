package impl;

import core.ComponentDrawer;
import core.Drawable;
import impl.tools.Edge;
import impl.tools.Tools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MyGraph implements Drawable {
    
    public int numOfNodes = 0;
    private int nextId = -1;
    
    Set<Node> nodes;
    Set<Edge> edges;
    
    private ComponentDrawer edgeDrawer = ComponentDrawer.getNullDrawer();
    
    // singleton
    private static final MyGraph instance = new MyGraph();
    public static MyGraph getInstance() { return instance; }
    private MyGraph() {
        nodes = new HashSet<>();
        edges = new HashSet<>();
    }
    
    public void clearGraph() {
        nextId = -1;
        numOfNodes = 0;
        nodes.clear();
        edges.clear();
    }
    
    @Override
    public void draw(Graphics2D g, AffineTransform at) {
        g.setColor(Color.BLACK);
        
        g.setStroke(Tools.BOLD_STROKE);
        edgeDrawer.draw(g, at, null);
        
        nodes.forEach(n -> n.draw(g, at));
    }
    
    // bad... slow
    public boolean connectById(int n1Id, int n2Id) {
        Optional<Node> n1 = nodes.stream().filter(n -> n.id == n1Id).findFirst();
        Optional<Node> n2 = nodes.stream().filter(n -> n.id == n2Id).findFirst();
        
        if (n1.isPresent() && n2.isPresent()) {
            return addEdge(n1.get(), n2.get());
        } else {
            throw new RuntimeException("Nodes not found by id: " + n1Id + ", " + n2Id + ".");
        }
    }
    
    public void deleteNode(Node node) {
        // bad... slow on deleting edges - iteration instead of set operation
        edges.removeIf(e -> e.getN1() == node || e.getN2() == node);
        
        node.neighbors.forEach(neigh ->  neigh.neighbors.remove(node));
        node.neighbors.clear(); // unnecessary
        
        if (nodes.remove(node)) return;
        throw new RuntimeException("Node " + node + " not removed!");
    }
    
    public void addNode(Node n) {
        nodes.add(n);
        numOfNodes++;
    }
    // TODO: MAKE A FACTORY METHOD FOR NODES
    
    public int getNextNodeId() {
        return ++nextId;
    }
    
    public boolean addEdge(Node n1, Node n2) {
        if (containsEdge(n1, n2) || containsEdge(n2, n1) || n1 == n2) return false;
        
        n1.neighbors.add(n2);
        n2.neighbors.add(n1);
        edges.add(new Edge(n1, n2));
        return true;
    }
    
    public boolean containsEdge(Node n1, Node n2) {
        return this.edges.contains(new Edge(n1, n2));
    }
    public Set<Node> getNodes() { return this.nodes; }
    
    public Set<Edge> getEdges() { return this.edges; }
    
    public void drawEdges(boolean draw) {
        this.edgeDrawer = draw ?
                ComponentDrawer.getEdgeDrawer(this.edges) : ComponentDrawer.getNullDrawer();
    }
    
    public Node getNodeById(int id) {
        Optional<Node> node = nodes.stream().filter(n -> n.id == id).findFirst();
        if (node.isPresent()) return node.get();
        throw new RuntimeException("Can't find node by id=" + id + ".");
    }
}
