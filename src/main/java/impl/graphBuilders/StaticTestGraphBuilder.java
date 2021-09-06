package impl.graphBuilders;

import impl.MyGraph;
import impl.Node;

public class StaticTestGraphBuilder extends GraphBuilder {
    
    public StaticTestGraphBuilder() {
        super();
    }
    
    @Override
    public void buildGraph() {
        Node n0  = MyGraph.getNode(); n0 .x=100; n0 .y = 280;
        Node n1  = MyGraph.getNode(); n1 .x=100; n1 .y = 100;
        Node n2  = MyGraph.getNode(); n2 .x=50 ; n2 .y = 170;
        Node n3  = MyGraph.getNode(); n3 .x=170; n3 .y = 250;
        Node n4  = MyGraph.getNode(); n4 .x=270; n4 .y = 210;
        Node n5  = MyGraph.getNode(); n5 .x=300; n5 .y = 100;
        Node n6  = MyGraph.getNode(); n6 .x=400; n6 .y = 170;
        Node n7  = MyGraph.getNode(); n7 .x=550; n7 .y = 250;
        Node n8  = MyGraph.getNode(); n8 .x=240; n8 .y = 370;
        Node n9  = MyGraph.getNode(); n9 .x=100; n9 .y = 500;
        Node n10 = MyGraph.getNode(); n10.x=101; n10.y = 670;
        Node n11 = MyGraph.getNode(); n11.x=400; n11.y = 600;
        Node n12 = MyGraph.getNode(); n12.x=400; n12.y = 480;
        Node n13 = MyGraph.getNode(); n13.x=370; n13.y = 380;
        
        Node n14 = MyGraph.getNode(); n14.x=600; n14.y = 600;
        
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
        
        n2.getState().setState(1);
        n8.getState().setState(1);
        this.initiallyInformedNodesNum = 2;
    }
}
