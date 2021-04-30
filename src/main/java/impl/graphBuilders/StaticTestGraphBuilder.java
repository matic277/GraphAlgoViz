package impl.graphBuilders;

import core.GraphBuilder;
import impl.MyGraph;
import impl.Node;

public class StaticTestGraphBuilder extends GraphBuilder {
    
    public StaticTestGraphBuilder() {
        super();
    }
    
    @Override
    public MyGraph buildGraph() {
        Node n0 = new Node(100, 280, this.graph.getNextNodeId());
        Node n1 = new Node(100, 100, this.graph.getNextNodeId());
        Node n2 = new Node(50, 170, this.graph.getNextNodeId());
        Node n3 = new Node(170, 250, this.graph.getNextNodeId());
        Node n4 = new Node(270, 210, this.graph.getNextNodeId());
        Node n5 = new Node(300, 100, this.graph.getNextNodeId());
        Node n6 = new Node(400, 170, this.graph.getNextNodeId());
        Node n7 = new Node(550, 250, this.graph.getNextNodeId());
        Node n8 = new Node(240, 370, this.graph.getNextNodeId());
        Node n9 = new Node(100, 500, this.graph.getNextNodeId());
        Node n10 = new Node(101, 670, this.graph.getNextNodeId());
        Node n11 = new Node(400, 600, this.graph.getNextNodeId());
        Node n12 = new Node(400, 480, this.graph.getNextNodeId());
        Node n13 = new Node(370, 380, this.graph.getNextNodeId());
        
        Node n14 = new Node(600, 600, this.graph.getNextNodeId());

        this.graph.addNode(n0);
        this.graph.addNode(n1);
        this.graph.addNode(n2);
        this.graph.addNode(n3);
        this.graph.addNode(n4);
        this.graph.addNode(n5);
        this.graph.addNode(n6);
        this.graph.addNode(n7);
        this.graph.addNode(n8);
        this.graph.addNode(n9);
        this.graph.addNode(n10);
        this.graph.addNode(n11);
        this.graph.addNode(n12);
        this.graph.addNode(n13);
        this.graph.addNode(n14);
    
        this.graph.addEdge(n0, n1);
        this.graph.addEdge(n1, n2);
        this.graph.addEdge(n1, n3);
    
        this.graph.addEdge(n3, n4);
        this.graph.addEdge(n4, n5);
        this.graph.addEdge(n5, n6);
        this.graph.addEdge(n6, n7);
        this.graph.addEdge(n7, n8);
        this.graph.addEdge(n8, n9);
        this.graph.addEdge(n9, n10);
        this.graph.addEdge(n10, n11);
        this.graph.addEdge(n11, n12);
        this.graph.addEdge(n12, n13);
    
        this.graph.addEdge(n12, n7);
        this.graph.addEdge(n11, n8);
        this.graph.addEdge(n3, n7);
        this.graph.addEdge(n8, n13);
        return this.graph;
    }
}
