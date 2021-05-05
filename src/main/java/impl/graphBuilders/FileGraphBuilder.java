package impl.graphBuilders;

import impl.Node;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.graph6.Graph6Sparse6Importer;

import core.GraphBuilder;
import impl.MyGraph;

import java.io.File;


public class FileGraphBuilder extends GraphBuilder {
    
    public FileGraphBuilder() {
        super();
    }
    
    @Override
    public MyGraph buildGraph() {
        // graph reading with JgraphT lib
        Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        Graph6Sparse6Importer<Integer, DefaultEdge> importer = new Graph6Sparse6Importer<>();
        var ref = new Object(){ int x = 0; };
        importer.setVertexFactory((t) -> ref.x++);
        importer.importGraph(graph, new File(this.fileName));
        
//        System.out.println(graph.vertexSet().size() + " | " + graph.edgeSet().size());
        
        // create nodes
        for (Integer node : graph.vertexSet()) {
            this.graph.addNode(new Node(node, -node, node));
        }
        // create edges
        for (DefaultEdge edge : graph.edgeSet()) {
            Integer n1 = graph.getEdgeSource(edge);
            Integer n2 = graph.getEdgeTarget(edge);
            this.graph.connectById(n1, n2);
        }
        
        // initialize nodes (informed or not)
        this.getNodeInformator().run();
        
        this.arrangeNodesInCircularLayout(400);
        return this.graph;
    }
}
