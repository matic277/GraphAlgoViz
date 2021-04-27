package impl.graphBuilders;

import core.GraphBuilder;
import impl.MyGraph;

public class EmptyGraphBuilder extends GraphBuilder {
    
    public EmptyGraphBuilder() {
        super();
    }
    
    @Override
    public MyGraph buildGraph() {
        return this.graph;
    }
}
