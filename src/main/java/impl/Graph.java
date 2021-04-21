package impl;

import core.Drawable;
import impl.tools.Edge;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.Set;

public class Graph implements Drawable {
    
    public static int numOfNodes = 0;
    
    Set<Node> nodes;
    Set<Edge> edges;
    
    public Graph() {
        nodes = new HashSet<>();
        edges = new HashSet<>();
        Node n1 = new Node(100, 100, 0);
        Node n2 = new Node(101, 170, 1);
        Node n3 = new Node(150, 250, 3);
        Node n4 = new Node(270, 280, 4);
        addNode(n1);
        addNode(n2);
        addNode(n3);
        addNode(n4);
        
        addEdge(n1, n2);
        addEdge(n2, n3);
        addEdge(n2, n4);

        n1.info = 1;
    }
    
    @Override
    public void draw(Graphics2D g, AffineTransform at) {
        g.setColor(Color.BLACK);
        for (Edge e : edges) {
            Node n1 = e.getN1();
            Node n2 = e.getN2();
            g.drawLine((int)n1.ts.getBounds().getCenterX(), (int)n1.ts.getBounds().getCenterY(),
                    (int)n2.ts.getBounds().getCenterX(), (int)n2.ts.getBounds().getCenterY());
        }
        nodes.forEach(n -> n.draw(g, at));
    }
    
    public void addNode(Node n) {
        nodes.add(n);
        numOfNodes++;
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
