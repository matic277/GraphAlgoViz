package core;

public interface GraphChangeObserver extends Observer {
    
    void onNodeAdded();
    void onNodeDeleted();
    void onEdgeAdded();
    void onNewInformedNode();
    void onNewUninformedNode();
    void onGraphClear();
}
