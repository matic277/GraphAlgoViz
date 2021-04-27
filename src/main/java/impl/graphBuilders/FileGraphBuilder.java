package impl.graphBuilders;

import core.GraphBuilder;
import impl.Graph;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileGraphBuilder extends GraphBuilder {
    
    public FileGraphBuilder() {
        super();
        
        
    }
    
    @Override
    public Graph buildGraph() {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(System.out::println);
        }
        catch (Exception e) {
            System.out.println("Error reading graph file \""+this.fileName+"\".");
            e.printStackTrace();
        }
        return this.graph;
    }
}
