package impl.graphBuilders;

import core.GraphBuilder;
import impl.MyGraph;
import impl.Node;
import impl.tools.Vector;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;

public class RandomGraphBuilder extends GraphBuilder {
    
    public RandomGraphBuilder() {
        super();
    }
    
    
    /**
     * Each edge in graph exists with given probability.
     * @return
     */
    @Override
    public MyGraph buildGraph() {
        System.out.println("building");
        // generate nodes
        for (int i=0; i<this.totalNodes; i++) {
            graph.addNode(new Node(i, -i,i));
        }
        
        // generate edges given probability
        Random r = new Random();
        
        for (Node n1 : graph.getNodes()) {
            for (Node n2 : graph.getNodes()) {
                if (n1 == n2) continue;
                if (r.nextDouble() <= this.edgeProbability) {
                    this.graph.addEdge(n1, n2);
                }
            }
        }
        
        this.arrangeNodesInCircularLayout(400);
        return this.graph;
    }
    
}
