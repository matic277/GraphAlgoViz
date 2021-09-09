package impl.graphBuilders;

import core.LayoutType;
import impl.MyGraph;
import impl.Node;
import impl.nodeinformator.NodeInformator;
import impl.nodeinformator.NodeInformatorProperties;
import impl.tools.Tools;
import org.jgrapht.alg.drawing.*;
import org.jgrapht.alg.drawing.model.Box2D;
import org.jgrapht.alg.drawing.model.LayoutModel2D;
import org.jgrapht.alg.drawing.model.MapLayoutModel2D;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

public abstract class GraphBuilder {
    
    protected MyGraph graph;
    protected int totalNodes;
    protected double edgeProbability;
    
    protected NodeInformatorProperties informatorProperties;
    protected NodeInformator nodeInformator;
    
    protected Double informedProbability;
    protected Integer totalInformed;
    
    protected int initiallyInformedNodesNum = 0;
    
    protected String fileName;
    
    public static LayoutType layoutType = LayoutType.values()[0]; // default
    
    public GraphBuilder() { this.graph = MyGraph.getInstance(); }
    
    public abstract void buildGraph();
    
    public static void indexedFrLayout() {
        // TODO
        // Needs:
        // Set<Node> partition, Comparator<Node> vertexComparator
        LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
        LayoutAlgorithm2D<Node, DefaultEdge> layout = new FRLayoutAlgorithm2D<>();
        layout.layout(MyGraph.getInstance().getGraph(), model);
        
        // set positions from model to nodes
        model.collect().forEach(Node::setPosition);
    }
    
    public static void frLayout() {
        // TODO
        // Needs:
        // Set<Node> partition, Comparator<Node> vertexComparator
        LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
        LayoutAlgorithm2D<Node, DefaultEdge> layout = new FRLayoutAlgorithm2D<>();
        layout.layout(MyGraph.getInstance().getGraph(), model);
        
        // set positions from model to nodes
        model.collect().forEach(Node::setPosition);
    }
    
    public static void medianGreedyBipartiteLayout() {
        // TODO
        // Needs:
        // Set<Node> partition, Comparator<Node> vertexComparator
        LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
        LayoutAlgorithm2D<Node, DefaultEdge> layout = new MedianGreedyTwoLayeredBipartiteLayout2D<>();
        layout.layout(MyGraph.getInstance().getGraph(), model);
        
        // set positions from model to nodes
        model.collect().forEach(Node::setPosition);
    }
    
    public static void barycenterGreedyBipartiteLayout() {
        // TODO
        // Needs:
        // Set<Node> partition, Comparator<Node> vertexComparator
        LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
        LayoutAlgorithm2D<Node, DefaultEdge> layout = new BarycenterGreedyTwoLayeredBipartiteLayout2D<>();
        layout.layout(MyGraph.getInstance().getGraph(), model);
        
        // set positions from model to nodes
        model.collect().forEach(Node::setPosition);
    }
    
    public static void twoLayeredBipartiteLayout() {
        // TODO
        // Needs:
        // Set<Node> partition, Comparator<Node> vertexComparator
        LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
        LayoutAlgorithm2D<Node, DefaultEdge> layout = new TwoLayeredBipartiteLayout2D<>();
        layout.layout(MyGraph.getInstance().getGraph(), model);
        
        // set positions from model to nodes
        model.collect().forEach(Node::setPosition);
    }
    
    public static void randomLayout() {
        LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
        LayoutAlgorithm2D<Node, DefaultEdge> layout = new RandomLayoutAlgorithm2D<>();
        layout.layout(MyGraph.getInstance().getGraph(), model);
        
        // set positions from model to nodes
        model.collect().forEach(Node::setPosition);
    }
    
    public static void circularLayout() {
        LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
        LayoutAlgorithm2D<Node, DefaultEdge> layout = new CircularLayoutAlgorithm2D<>(390);
        layout.layout(MyGraph.getInstance().getGraph(), model);

        // set positions from model to nodes
        model.collect().forEach(Node::setPosition);
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
    
    public GraphBuilder setInformatorProperties(NodeInformatorProperties properties) {
        this.informatorProperties = properties;
        return this;
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
    
    public GraphBuilder setInformedProbability(Double probability) {
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
        return new NodeInformator(this.informatorProperties);
    }
    
    protected void arrangeNodesInCircularLayoutJGraphT(){};
}
