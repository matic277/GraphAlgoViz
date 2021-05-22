package impl.graphBuilders;

import impl.MyGraph;
import impl.Node;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.graph6.Graph6Sparse6Importer;

import java.io.File;


public class FileGraphBuilder extends GraphBuilder {
    
    public FileGraphBuilder() {
        super();
    }
    
    @Override
    public void buildGraph() {
        // graph reading with JGraphT lib
        Graph6Sparse6Importer<Node, DefaultEdge> importer = new Graph6Sparse6Importer<>();
        importer.setVertexFactory((t) -> MyGraph.getNode());
        importer.importGraph(this.graph.getGraph(), new File(this.fileName));
    
        System.out.println("Graph="+graph.getNodes().size());
        
        
        
        // random
//        LayoutModel2D<Integer> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
//        RandomLayoutAlgorithm2D<Integer, DefaultEdge> rand = new RandomLayoutAlgorithm2D<>();
//        rand.layout(graph, model);
        
        // map layout
//        LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
//        LayoutAlgorithm2D<Node, DefaultEdge> rand = new FRLayoutAlgorithm2D<>();
//        rand.layout(this.graph.getGraph(), model);
//
//        // set positions from model to nodes
//        model.collect().forEach(Node::setPosition);
        
        // initialize nodes (informed or not)
        this.getNodeInformator().run();
        
        this.arrangeNodesInCircularLayoutJGraphT();
    }
}
