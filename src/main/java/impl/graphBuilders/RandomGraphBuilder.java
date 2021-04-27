package impl.graphBuilders;

import core.GraphBuilder;
import impl.Graph;

public class RandomGraphBuilder extends GraphBuilder {
    
    public RandomGraphBuilder() {
        super();
    }
    
    @Override
    public Graph buildGraph() {
        return this.graph;
    }
}
