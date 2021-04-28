package core;

import impl.graphBuilders.*;
import impl.graphOptionPanels.*;

public enum GraphType {
    
    // testing purposes
    STATIC_TEST(0, "STATIC_TEST", StaticTestGraphOptionPanel.getInstance(), new StaticTestGraphBuilder()),
    
    RANDOM(1, "Random graph", RandomGraphOptionPanel.getInstance(), new RandomGraphBuilder()),
    CUSTOM(2, "Load from file", CustomGraphOptionPanel.getInstance(), new FileGraphBuilder()),
    USER(3, "Create your own", UserGraphOptionPanel.getInstance(), new EmptyGraphBuilder()),
    CLIQUE(4, "Fully connected (clique)", CliqueGraphOptionPanel.getInstance(), new CliqueGraphBuilder());
    
    private int id;
    private String description;
    private OptionPanel panel;
    private GraphBuilder builder;
    
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
