package impl.graphBuilders;

import core.LayoutType;
import impl.MyGraph;
import impl.Node;
import impl.tools.Tools;
import impl.tools.Vector;
import org.jgrapht.alg.drawing.*;
import org.jgrapht.alg.drawing.model.Box2D;
import org.jgrapht.alg.drawing.model.LayoutModel2D;
import org.jgrapht.alg.drawing.model.MapLayoutModel2D;
import org.jgrapht.graph.DefaultEdge;

import java.awt.*;
import java.util.*;

public abstract class GraphBuilder {
    
    protected MyGraph graph;
    protected int totalNodes;
    protected double edgeProbability;
    
    protected Integer informedProbability;
    protected Integer totalInformed;
    
    protected int initiallyInformedNodesNum = 0;
    
    protected String fileName;
    
    public static LayoutType layoutType = LayoutType.values()[0]; // default
    protected static Map<LayoutType, Runnable> layoutTypeMap = new HashMap<>(); {
        layoutTypeMap.put(LayoutType.CIRCULAR, circularLayout());
        layoutTypeMap.put(LayoutType.RANDOM, randomLayout());
        layoutTypeMap.put(LayoutType.TWO_LAYERED_BIPARTITE, twoLayeredBipartiteLayout());
        layoutTypeMap.put(LayoutType.BARY_CENTER_GREEDY_TWO_LAYERED_BIPARTITE, barycenterGreedyBipartiteLayout());
        layoutTypeMap.put(LayoutType.MEDIAN_GREEDY_TWO_LAYERED_BIPARTITE, medianGreedyBipartiteLayout());
        layoutTypeMap.put(LayoutType.FR, frLayout());
        layoutTypeMap.put(LayoutType.INDEXED_FR, indexedFrLayout());
        
        if (layoutTypeMap.size() != LayoutType.values().length)
            throw new RuntimeException("Expected " + LayoutType.values().length +
                                       " entries in typeMap, but found only " + layoutTypeMap.size() + ".");
    }
    
    public GraphBuilder() { this.graph = MyGraph.getInstance(); }
    
    public abstract void buildGraph();
    
    public Runnable indexedFrLayout() {
        return () -> {
            // TODO
            // Needs:
            // Set<Node> partition, Comparator<Node> vertexComparator
            LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
            LayoutAlgorithm2D<Node, DefaultEdge> layout = new FRLayoutAlgorithm2D<>();
            layout.layout(this.graph.getGraph(), model);
            
            // set positions from model to nodes
            model.collect().forEach(Node::setPosition);
        };
    }
    
    public Runnable frLayout() {
        return () -> {
            // TODO
            // Needs:
            // Set<Node> partition, Comparator<Node> vertexComparator
            LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
            LayoutAlgorithm2D<Node, DefaultEdge> layout = new FRLayoutAlgorithm2D<>();
            layout.layout(this.graph.getGraph(), model);
            
            // set positions from model to nodes
            model.collect().forEach(Node::setPosition);
        };
    }
    
    public Runnable medianGreedyBipartiteLayout() {
        return () -> {
            // TODO
            // Needs:
            // Set<Node> partition, Comparator<Node> vertexComparator
            LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
            LayoutAlgorithm2D<Node, DefaultEdge> layout = new MedianGreedyTwoLayeredBipartiteLayout2D<>();
            layout.layout(this.graph.getGraph(), model);
            
            // set positions from model to nodes
            model.collect().forEach(Node::setPosition);
        };
    }
    
    public Runnable barycenterGreedyBipartiteLayout() {
        return () -> {
            // TODO
            // Needs:
            // Set<Node> partition, Comparator<Node> vertexComparator
            LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
            LayoutAlgorithm2D<Node, DefaultEdge> layout = new BarycenterGreedyTwoLayeredBipartiteLayout2D<>();
            layout.layout(this.graph.getGraph(), model);
            
            // set positions from model to nodes
            model.collect().forEach(Node::setPosition);
        };
    }
    
    public Runnable twoLayeredBipartiteLayout() {
        return () -> {
            // TODO
            // Needs:
            // Set<Node> partition, Comparator<Node> vertexComparator
            LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
            LayoutAlgorithm2D<Node, DefaultEdge> layout = new TwoLayeredBipartiteLayout2D<>();
            layout.layout(this.graph.getGraph(), model);
            
            // set positions from model to nodes
            model.collect().forEach(Node::setPosition);
        };
    }
    
    public Runnable randomLayout() {
        return () -> {
            LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
            LayoutAlgorithm2D<Node, DefaultEdge> layout = new RandomLayoutAlgorithm2D<>();
            layout.layout(this.graph.getGraph(), model);
            
            // set positions from model to nodes
            model.collect().forEach(Node::setPosition);
        };
    }
    
    public Runnable circularLayout() {
        return () -> {
            LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
            LayoutAlgorithm2D<Node, DefaultEdge> layout = new CircularLayoutAlgorithm2D<>(390);
            layout.layout(this.graph.getGraph(), model);
    
            // set positions from model to nodes
            model.collect().forEach(Node::setPosition);
        };
    }
    
    public void arrangeNodesInGridLayout(int columns) {
        // place nodes in (columns x INF) grid
        // separated by dx and dy pixels
        int dx = 100, dy = 100;
        Iterator<Node> iter = this.graph.getNodes().iterator();
        for (int y=50, i=0; iter.hasNext(); y+=dy, i++) {
            for (int x=50, j=0; j<columns && iter.hasNext(); x+=dx, j++) {
                Node n = iter.next();
                n.x = x; n.y = y;
            }
        }
    }
    
    public GraphBuilder setNumberOfNodes(int nodes) {
        this.totalNodes = nodes;
        return this;
    }
    
    public GraphBuilder setEdgeProbability(double prob) {
        this.edgeProbability = prob;
        return this;
    }
    
    public GraphBuilder setFileName(String name) {
        this.fileName = name;
        return this;
    }
    
    public GraphBuilder setInformedProbability(Integer probability) {
        this.informedProbability = probability;
        return this;
    }
    
    public GraphBuilder setTotalInformed(Integer totalInformed) {
        this.totalInformed = totalInformed;
        return this;
    }
    
    public int getNumberOfInitiallyInformedNodes() { return this.initiallyInformedNodesNum; }
    
    // This is slow in some cases
    // TODO optimize when creating nodes in the first place
    public Runnable getNodeInformator() {
        // If user specified:
        //  number of informed nodes = 100
        //  but graph has less than 100 nodes
        //  then this must be corrected !!!
        //  (otherwise this runnable hangs)
        if (this.totalInformed != null && this.totalInformed > graph.getNodes().size()) {
            this.totalInformed = Math.min(graph.getNodes().size(), this.totalInformed);
        }
        
        return totalInformed != null ?
                // inform number of nodes (randomly)
                () -> {
                    this.initiallyInformedNodesNum = totalInformed;
                    Set<Integer> alreadyInformed = new HashSet<>(totalInformed);
                    while (totalInformed-- > 0) {
                        int randId = Tools.RAND.nextInt(this.graph.getNodes().size());
                        if (alreadyInformed.contains(randId)) {
                            totalInformed++;
                            continue;
                        }
                        alreadyInformed.add(randId);
                        this.graph.getNodeById(randId).states.get(0).setState(1);
                    }
                } :
                // inform based on probability (randomly)
                () -> {
                    this.graph.getNodes().forEach(n -> {
                            boolean inform = Tools.RAND.nextInt(100) <= informedProbability;
                            if (inform) this.initiallyInformedNodesNum++;
                            n.getState().setState(inform ? 1 : 0);
                    });
                };
    }
    
    protected void arrangeNodesInCircularLayoutJGraphT(){};
}
