package core;

import impl.graphBuilders.*;
import impl.panels.importPanels.graphOptionPanels.*;


public enum GraphType {
    
    // testing purposes
    STATIC_TEST(0, "STATIC_TEST",              StaticTestGraphOptionPanel.getInstance(), new StaticTestGraphBuilder()),
    RANDOM(     1, "Random graph",             RandomGraphOptionPanel.getInstance(),     new RandomGraphBuilder()),
    FILE(       2, "Load from file",           FileGraphOptionPanel.getInstance(),       new FileGraphBuilder()),
    USER(       3, "Create your own",          UserGraphOptionPanel.getInstance(),       new EmptyGraphBuilder()),
    CLIQUE(     4, "Fully connected (clique)", CliqueGraphOptionPanel.getInstance(),     new CliqueGraphBuilder());
    
    private final int id;
    private final String description;
    private final OptionPanel panel;
    private final GraphBuilder builder;
    
    GraphType(int id, String desc, OptionPanel panel, GraphBuilder builder) {
        this.id = id;
        this.description = desc;
        this.panel = panel;
        this.builder = builder;
    }
    
    public static GraphType getByDescription(String desc) {
        for (GraphType value : GraphType.values()) {
            if (value.description.equals(desc)) return value;
        }
        throw new RuntimeException("Unknown graph type " + desc + ".");
    }
    
    public OptionPanel getPanel() { return this.panel; }
    public GraphBuilder getGraphBuilder() { return this.builder; }
    
    @Override
    public String toString() {
        return description;
    }
}
