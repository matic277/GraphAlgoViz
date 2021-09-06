package core;

import impl.Node;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphListener;
import org.jgrapht.graph.DefaultEdge;

public interface GraphChangeObserver extends GraphListener<Node, DefaultEdge> {
    
    // Inherited from GraphListener<>:
    //   edgeRemoved
    //   edgeAdded
    //   vertexAdded
    //   vertexRemoved
    
    void onGraphClear();
    void onGraphImport();
    
    void onNewInformedNode();
    void onNewUninformedNode();
}
