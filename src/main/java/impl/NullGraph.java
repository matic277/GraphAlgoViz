package impl;

import impl.tools.Edge;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.Set;

public class NullGraph extends MyGraph {
    
    private static final NullGraph instance = new NullGraph();
    public static NullGraph getInstance() { return instance; }
    
    private NullGraph() {}
    
    @Override
    public void draw(Graphics2D g, AffineTransform at) {
    
    }
    
    public boolean connectById(int n1Id, int n2Id) {
        return false;
    }
    
    public void deleteNode(Node node) { }
    
    public void addNode(Node n) { }
    
    public int getNextNodeId() {
        return -1;
    }
    
    public boolean addEdge(Node n1, Node n2) {
        return false;
    }
    
    public boolean containsEdge(Node n1, Node n2) {
        return false;
    }
    public Set<Node> getNodes() { return new HashSet<>(); }
    
    public Set<Edge> getEdges() { return new HashSet<>(); }
    
    public void drawEdges(boolean draw) {
    }
    
    public Node getNodeById(int id) {
        return null;
    }
}
