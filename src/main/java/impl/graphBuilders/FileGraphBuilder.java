package impl.graphBuilders;

import impl.Node;
import org.jgrapht.Graph;
import org.jgrapht.alg.drawing.*;
import org.jgrapht.alg.drawing.model.Box2D;
import org.jgrapht.alg.drawing.model.LayoutModel2D;
import org.jgrapht.alg.drawing.model.MapLayoutModel2D;
import org.jgrapht.alg.drawing.model.Point2D;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.graph6.Graph6Sparse6Importer;

import core.GraphBuilder;

import java.awt.*;
import java.io.File;


public class FileGraphBuilder extends GraphBuilder {
    
    public FileGraphBuilder() {
        super();
    }
    
    @Override
    public void buildGraph() {
        // graph reading with JGraphT lib
        Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        Graph6Sparse6Importer<Integer, DefaultEdge> importer = new Graph6Sparse6Importer<>();
        var ref = new Object(){ int x = 0; };
        importer.setVertexFactory((t) -> ref.x++);
        importer.importGraph(graph, new File(this.fileName));
        
        // random
//        LayoutModel2D<Integer> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
//        RandomLayoutAlgorithm2D<Integer, DefaultEdge> rand = new RandomLayoutAlgorithm2D<>();
//        rand.layout(graph, model);
        
        LayoutModel2D<Integer> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
        LayoutAlgorithm2D<Integer, DefaultEdge> rand = new FRLayoutAlgorithm2D<>();
        rand.layout(graph, model);
        
        
        // Covert from JGraphT to MyGraph
        // create nodes
        for (Integer node : graph.vertexSet()) {
            Node n = new Node(node, -node, node);
            Point2D p = model.get(node);
            n.x = p.getX();
            n.y = p.getY();
            this.graph.addNode(n);
        }
        // create edges
        for (DefaultEdge edge : graph.edgeSet()) {
            Integer n1 = graph.getEdgeSource(edge);
            Integer n2 = graph.getEdgeTarget(edge);
            this.graph.connectById(n1, n2);
        }
        
        // initialize nodes (informed or not)
        this.getNodeInformator().run();
        
//        this.arrangeNodesInCircularLayout(400);
        
        
        
        
        
        
        Graph<Node, DefaultEdge> graph2 = new SimpleGraph<>(DefaultEdge.class);
        Graph6Sparse6Importer<Node, DefaultEdge> importer2 = new Graph6Sparse6Importer<>();
        var ref2 = new Object(){ int x = 0; };
        importer2.setVertexFactory((t) -> new Node(-ref2.x, ref2.x, ref2.x++));
        importer2.importGraph(graph2, new File(this.fileName));
        
        
        
        
        
        
        
    }
}
