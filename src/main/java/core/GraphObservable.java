package core;

public interface GraphObservable {
    
    void addObserver(GraphChangeObserver observer);
    void removeObserver(GraphChangeObserver observer);
}
