package impl;

public class State {
    
    int info;
    
    public State(int info) {
        this.info = info;
    }
    
    public State() {
        this(0);
    }
    
    public int getState() {
        return info;
    }
    
    public void setState(int info) {
        this.info = info;
    }
}
