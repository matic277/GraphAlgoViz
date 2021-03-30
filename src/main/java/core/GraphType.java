package core;

public enum GraphType {
    
    RANDOM(0, "Random graph"),
    CUSTOM(1, "Load from file"),
    USER(2, "Create your own");
    
    private int id;
    private String description;
    
    GraphType(int id, String desc) {
        this.id = id;
        this.description = desc;
    }
    
    public static GraphType getByDescription(String desc) {
        for (GraphType value : GraphType.values()) {
            if (value.description.equals(desc)) return value;
        }
        throw new RuntimeException("Unknown graph type " + desc + ".");
    }
    
    @Override
    public String toString() {
        return description;
    }
}
