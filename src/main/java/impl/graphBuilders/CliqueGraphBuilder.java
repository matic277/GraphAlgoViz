package impl.graphBuilders;

import impl.MyGraph;
import impl.Node;

public class CliqueGraphBuilder extends GraphBuilder {
    
    @Override
    public void buildGraph() {
        for (int i=0; i<this.totalNodes; i++) {
            this.graph.addNode(MyGraph.getNode());
        }
        
        for (Node n1 : this.graph.getNodes()) {
            for (Node n2 : this.graph.getNodes()) {
                if (n1 == n2) continue;
                this.graph.addEdge(n1, n2);
            }
        }
        
        // TODO
        // instead of every (almost )implementor having to call this
        // something better could be written, but i'm lazy right now...
        // or maybe not, since on empty/static import graph implementors
        // there is no point. but maybe these two lines should be a method
        // in abstract class GraphBuilder, and the implementing classes
        // can overwrite with empty method body so nothing happens?
        // (in this case whoever is calling this method must also call this new method mentioned above)
        this.getNodeInformator().run();
        GraphBuilder.layoutType.getLayoutExecutor().run();
    }
}
