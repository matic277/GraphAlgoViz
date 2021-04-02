package core;

import impl.graphOptionPanels.CustomGraphOptionPanel;
import impl.graphOptionPanels.RandomGraphOptionPanel;
import impl.graphOptionPanels.UserGraphOptionPanel;

public enum GraphType {
    
    RANDOM(0, "Random graph", RandomGraphOptionPanel.getInstance()),
    CUSTOM(1, "Load from file", CustomGraphOptionPanel.getInstance()),
    USER(2, "Create your own", UserGraphOptionPanel.getInstance());
    
    private int id;
    private String description;
    private OptionPanel panel;
    
    GraphType(int id, String desc, OptionPanel panel) {
        this.id = id;
        this.description = desc;
        this.panel = panel;
    }
    
    public static GraphType getByDescription(String desc) {
        for (GraphType value : GraphType.values()) {
            if (value.description.equals(desc)) return value;
        }
        throw new RuntimeException("Unknown graph type " + desc + ".");
    }
    
    public OptionPanel getPanel() {
        return this.panel;
    }
    
    @Override
    public String toString() {
        return description;
    }
}
