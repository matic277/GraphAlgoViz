package core;

import impl.Graph;

public abstract class GraphBuilder {
    
    protected Graph graph;
    protected int totalNodes;
    protected double edgeProbability;
    
    public GraphBuilder() {
        this.graph = new Graph();
    }
    
    public abstract Graph buildGraph();
    
    public GraphBuilder setNumberOfNodes(int nodes) {
        this.totalNodes = nodes;
        return this;
    }
    
    public GraphBuilder setEdgeProbability(double prob) {
        this.edgeProbability = prob;
        return this;
    }
}
