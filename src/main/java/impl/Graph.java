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
        Node n0 = new Node(100, 280, 0);
        Node n1 = new Node(100, 100, 1);
        Node n2 = new Node(50, 170, 2);
        Node n3 = new Node(170, 250, 3);
        Node n4 = new Node(270, 210, 4);
        Node n5 = new Node(300, 100, 5);
        Node n6 = new Node(400, 170, 6);
        Node n7 = new Node(550, 250, 7);
        Node n8 = new Node(240, 370, 8);
        Node n9 = new Node(100, 500, 9);
        Node n10 = new Node(101, 670, 10);
        Node n11 = new Node(400, 600, 11);
        Node n12 = new Node(400, 480, 12);
        Node n13 = new Node(370, 380, 13);
        addNode(n0);
        addNode(n1);
        addNode(n2);
        addNode(n3);
        addNode(n4);
        addNode(n5);
        addNode(n6);
        addNode(n7);
        addNode(n8);
        addNode(n9);
        addNode(n10);
        addNode(n11);
        addNode(n12);
        addNode(n13);
        
        addEdge(n0, n1);
        addEdge(n1, n2);
        addEdge(n1, n3);
    
        addEdge(n3, n4);
        addEdge(n4, n5);
        addEdge(n5, n6);
        addEdge(n6, n7);
        addEdge(n7, n8);
        addEdge(n8, n9);
        addEdge(n9, n10);
        addEdge(n10, n11);
        addEdge(n11, n12);
        addEdge(n12, n13);
        
        addEdge(n12, n7);
        addEdge(n11, n8);
        addEdge(n3, n7);
        addEdge(n8, n13);
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
