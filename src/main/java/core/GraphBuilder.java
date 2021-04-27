package core;

import impl.MyGraph;

public abstract class GraphBuilder {
    
    protected MyGraph graph;
    protected int totalNodes;
    protected double edgeProbability;
    
    protected String fileName;
    
    public GraphBuilder() {
        this.graph = new MyGraph();
    }
    
    public abstract MyGraph buildGraph();
    
    public GraphBuilder setNumberOfNodes(int nodes) {
        this.totalNodes = nodes;
        return this;
    }
    
    public GraphBuilder setEdgeProbability(double prob) {
        this.edgeProbability = prob;
        return this;
    }
    
    public GraphBuilder setFileName(String name) {
        this.fileName = name;
        return this;
    }
}
