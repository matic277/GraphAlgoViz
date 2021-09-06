package impl.graphBuilders;

import impl.MyGraph;
import impl.Node;
import org.jgrapht.GraphType;
import org.jgrapht.alg.drawing.model.MapLayoutModel2D;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.graph6.Graph6Sparse6Importer;

import java.io.File;


public class FileGraphBuilder extends GraphBuilder {
    
    public FileGraphBuilder() {
        super(); // TODO i don't remember these calls being necessary, remove clutter in all implementors
    }
    
    @Override
    public void buildGraph() {
        // graph reading with JGraphT lib
        Graph6Sparse6Importer<Node, DefaultEdge> importer = new Graph6Sparse6Importer<>();
        importer.setVertexFactory((t) -> MyGraph.getNode());
        importer.importGraph(this.graph.getGraph(), new File(this.fileName));
        
        // initialize nodes (informed or not)
        this.getNodeInformator().run();
        GraphBuilder.layoutType.getLayoutExecutor().run();
    }
}
