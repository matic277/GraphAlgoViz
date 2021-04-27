package impl.graphBuilders;

import core.GraphBuilder;
import impl.Graph;

public class FileGraphBuilder extends GraphBuilder {
    
    public FileGraphBuilder() {
        super();
    }
    
    @Override
    public Graph buildGraph() {
        return this.graph;
    }
}
