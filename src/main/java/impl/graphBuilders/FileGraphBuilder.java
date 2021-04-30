package impl.graphBuilders;

import core.GraphBuilder;
import impl.MyGraph;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class FileGraphBuilder extends GraphBuilder {
    
    public FileGraphBuilder() {
        super();
    }
    
    @Override
    public MyGraph buildGraph() {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(System.out::println);
        }
        catch (Exception e) {
            System.out.println("Error reading graph file \""+this.fileName+"\".");
            e.printStackTrace();
        }
        return this.graph;
    }
    
    public void random() {
//        // random graph
//        int nodes = 10;
//        int edges = 15;
//
//
//        Supplier<Integer> vSupplier = new Supplier<Integer>() {
//            private int id = 0;
//            public Integer get() { return id++; }
//        };
//
//        GnmRandomGraphGenerator<Integer, DefaultEdge> generator = new GnmRandomGraphGenerator<>(nodes, edges);
//        var rg = new SimpleGraph<Integer, DefaultEdge>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);
//        generator.generateGraph(rg, null);
    }
}
