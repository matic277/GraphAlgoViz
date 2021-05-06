package impl.graphBuilders;

import core.GraphBuilder;
import impl.MyGraph;
import impl.Node;

public class CliqueGraphBuilder extends GraphBuilder {
    
    @Override
    public MyGraph buildGraph() {
        for (int i=0; i<this.totalNodes; i++) {
            this.graph.addNode(new Node(i, -i, this.graph.getNextNodeId()));
        }
        
        for (Node n1 : this.graph.getNodes()) {
            for (Node n2 : this.graph.getNodes()) {
                if (n1 == n2) continue;
                this.graph.addEdge(n1, n2);
            }
        }
        
        this.getNodeInformator().run();
        
        this.arrangeNodesInCircularLayout(400);
        return this.graph;
    }
}
