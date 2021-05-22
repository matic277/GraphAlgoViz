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
        
        this.getNodeInformator().run();
        GraphBuilder.layoutTypeMap.get(GraphBuilder.layoutType).run();
    }
}
