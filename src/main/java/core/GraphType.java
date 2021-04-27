package core;

import impl.graphBuilders.EmptyGraphBuilder;
import impl.graphBuilders.FileGraphBuilder;
import impl.graphBuilders.RandomGraphBuilder;
import impl.graphOptionPanels.CustomGraphOptionPanel;
import impl.graphOptionPanels.RandomGraphOptionPanel;
import impl.graphOptionPanels.UserGraphOptionPanel;

public enum GraphType {
    
    RANDOM(0, "Random graph", RandomGraphOptionPanel.getInstance(), new RandomGraphBuilder()),
    CUSTOM(1, "Load from file", CustomGraphOptionPanel.getInstance(), new EmptyGraphBuilder()),
    USER(2, "Create your own", UserGraphOptionPanel.getInstance(), new FileGraphBuilder());
    
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
