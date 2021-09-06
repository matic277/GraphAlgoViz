package core;

import impl.graphBuilders.GraphBuilder;

public enum LayoutType {
    
    CIRCULAR(  0, "Circular",   GraphBuilder::circularLayout),
    RANDOM(    1, "Random",     GraphBuilder::randomLayout),
    FR(        2, "FR",         GraphBuilder::frLayout),
    INDEXED_FR(3, "Indexed FR", GraphBuilder::indexedFrLayout),
    
    TWO_LAYERED_BIPARTITE(                   4, "Two layered bipartite",                  GraphBuilder::twoLayeredBipartiteLayout),
    BARY_CENTER_GREEDY_TWO_LAYERED_BIPARTITE(5, "Barycenter greedy two layered biparite", GraphBuilder::barycenterGreedyBipartiteLayout),
    MEDIAN_GREEDY_TWO_LAYERED_BIPARTITE(     6, "Median greedy two layered bipartite",    GraphBuilder::medianGreedyBipartiteLayout)
    ;
    
    private final int id;
    private final String displayValue;
    private final Runnable layoutExecutor; // this calls the layout emthods on graph
    
    LayoutType(int id_, String displayValue_, Runnable layExec_) { id=id_; displayValue=displayValue_; layoutExecutor=layExec_; }
    
    public int getId() { return id; }
    public String getDisplayValue() { return displayValue; }
    public Runnable getLayoutExecutor() { return layoutExecutor; }
    
    @Override
    public String toString() { return displayValue; }
}
