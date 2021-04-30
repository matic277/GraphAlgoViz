package impl.graphBuilders;

import core.GraphBuilder;
import impl.MyGraph;
import impl.Node;
import impl.tools.Tools;

public class RandomGraphBuilder extends GraphBuilder {
    
    public RandomGraphBuilder() {
        super();
    }
    
    
    /**
     * Each edge in graph exists with given probability.
     */
    @Override
    public MyGraph buildGraph() {
        // generate nodes
        for (int i=0; i<this.totalNodes; i++) {
            graph.addNode(new Node(i, -i,this.graph.getNextNodeId()));
        }
        
        // generate edges given probability
        for (Node n1 : graph.getNodes()) {
            for (Node n2 : graph.getNodes()) {
                if (n1 == n2) continue;
                if (Tools.RAND.nextDouble() <= this.edgeProbability) {
                    this.graph.addEdge(n1, n2);
                }
            }
        }
        
        this.arrangeNodesInCircularLayout(400);
        return this.graph;
    }
    
}
