package impl;

import core.ComponentDrawer;
import core.Drawable;
import impl.tools.Edge;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class MyGraph implements Drawable {
    
    public static int numOfNodes = 0;
    private int nextId = -1;
    
    Set<Node> nodes;
    Set<Edge> edges;
    
    private ComponentDrawer edgeDrawer = ComponentDrawer.getNullDrawer(); // Initialized will set this to default when graph is built!
    
    public MyGraph() {
        nodes = new HashSet<>();
        edges = new HashSet<>();
    }
    
    @Override
    public void draw(Graphics2D g, AffineTransform at) {
        g.setColor(Color.BLACK);
        nodes.forEach(n -> n.draw(g, at));
        edgeDrawer.draw(g, at, null);
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
    
    // bad... slow
    public void deleteNode(Node node) {
        edges.removeIf(e -> e.getN1() == node || e.getN2() == node);
        node.neighbors.forEach(neigh ->  neigh.neighbors.remove(node));
        node.neighbors.clear(); // unnecessary
        
        // Can't use HashSet.remove on mutating elements...
        // https://bugs.openjdk.java.net/browse/JDK-8154740
//        nodes.removeIf(n -> n.id == node.id);
        
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
}
