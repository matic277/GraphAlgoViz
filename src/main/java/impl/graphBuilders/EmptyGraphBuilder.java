package impl.graphBuilders;

import core.GraphBuilder;
import impl.Graph;

public class EmptyGraphBuilder extends GraphBuilder {
    
    public EmptyGraphBuilder() {
        super();
    }
    
    @Override
    public Graph buildGraph() {
        return this.graph;
    }
}
