package core;

public enum LayoutType {
    
    CIRCULAR(0, "Circular"),
    RANDOM(1, "Random"),
    TWO_LAYERED_BIPARTITE(2, "Two layered bipartite"),
    BARY_CENTER_GREEDY_TWO_LAYERED_BIPARTITE(3, "Barry center greedy two layered biparite"),
    MEDIAN_GREEDY_TWO_LAYERED_BIPARTITE(4, "Median greedy two layered bipartite"),
    FR(5, "FR"),
    INDEXED_FR(6, "Indexed FR"),
    ;
    
    int id;
    String displayValue;
    
    LayoutType(int id_, String displayValue_) { id = id_; displayValue = displayValue_; }
    
//    private static Map<LayoutType,>
    
    @Override
    public String toString() { return displayValue; }
}
