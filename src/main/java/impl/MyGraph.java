package impl;

import core.Drawable;
import impl.tools.Edge;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.Set;

public class MyGraph implements Drawable {
    
    public static int numOfNodes = 0;
    private int nextId = -1;
    
    Set<Node> nodes;
    Set<Edge> edges;
    
    public MyGraph() {
        nodes = new HashSet<>();
        edges = new HashSet<>();
    }
    
    @Override
    public void draw(Graphics2D g, AffineTransform at) {
        g.setColor(Color.BLACK);
        nodes.forEach(n -> n.draw(g, at));
        g.setColor(Color.black);
        for (Edge e : edges) {
            Node n1 = e.getN1();
            Node n2 = e.getN2();
            g.drawLine((int)n1.ts.getBounds().getCenterX(), (int)n1.ts.getBounds().getCenterY(),
                    (int)n2.ts.getBounds().getCenterX(), (int)n2.ts.getBounds().getCenterY());
        }
    }
    
    
    public void addNode(Node n) {
        nodes.add(n);
        numOfNodes++;
    }
    // TODO: MAKE A FACTORY METHOD FOR NODES
    public int getNextNodeId() {
        return ++nextId;
    }
    
    public void addEdge(Node n1, Node n2) {
        if (containsEdge(n1, n2) || containsEdge(n2, n1) || n1 == n2) return;
        
        n1.neighbors.add(n2);
        n2.neighbors.add(n1);
        edges.add(new Edge(n1, n2));
    }
    
    public boolean containsEdge(Node n1, Node n2) {
        return this.edges.contains(new Edge(n1, n2));
    }
    
    public Set<Node> getNodes() {
        return this.nodes;
    }
}
