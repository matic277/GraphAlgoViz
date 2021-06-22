package impl;

import impl.Node;
import impl.State;

import java.util.List;

public class Vertex {
    
    Node node;
    
    // Wrapper class for Node
    public Vertex(Node node) {
        this.node = node;
    }
    
    public List<Node> getNeighbors() {
        return node.getNeighbors();
    }
    
    public State getState() {
        return node.getState();
    }
}
