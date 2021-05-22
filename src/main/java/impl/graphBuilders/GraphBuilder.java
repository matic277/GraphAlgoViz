package impl.graphBuilders;

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class GraphBuilder {
    
    protected MyGraph graph;
    protected int totalNodes;
    protected double edgeProbability;
    
    protected Integer informedProbability;
    protected Integer totalInformed;
    
    protected int initiallyInformedNodesNum = 0;
    
    protected String fileName;
    
    public GraphBuilder() { this.graph = MyGraph.getInstance(); }
    
    public abstract void buildGraph();
    
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
    
    public void arrangeNodesInCircularLayoutJGraphT() {
        LayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000, 800));
        LayoutAlgorithm2D<Node, DefaultEdge> layout = new CircularLayoutAlgorithm2D<>(390);
        layout.layout(this.graph.getGraph(), model);
        
        // set positions from model to nodes
        model.collect().forEach(Node::setPosition);
    }
    
    private void a() {
        // Needs:
        // Set<Node> partition, Comparator<Node> vertexComparator
        TwoLayeredBipartiteLayout2D<Node, DefaultEdge>   x9 = new TwoLayeredBipartiteLayout2D<>();
        
        // Needs:
        // Set<Node> partition, Comparator<Node> vertexComparator
        BarycenterGreedyTwoLayeredBipartiteLayout2D<Node, DefaultEdge>   x1 = new BarycenterGreedyTwoLayeredBipartiteLayout2D<>();
        
        // Needs:
        // Set<Node> partition, Comparator<Node> vertexComparator
        MedianGreedyTwoLayeredBipartiteLayout2D<Node, DefaultEdge>   x6 = new MedianGreedyTwoLayeredBipartiteLayout2D<>();
        
        // Needs:
        // (int iterations, double normalizationFactor), or just iterations
        FRLayoutAlgorithm2D<Node, DefaultEdge>   x4 = new FRLayoutAlgorithm2D<>();
        
        // Needs
        // (int iterations, double theta, double normalizationFactor)
        // (int iterations)
        // or nothing
        IndexedFRLayoutAlgorithm2D<Node, DefaultEdge>    x5 = new IndexedFRLayoutAlgorithm2D<>();
        
        // Needs: nothing
        RandomLayoutAlgorithm2D<Node, DefaultEdge>   x7 = new RandomLayoutAlgorithm2D<>();
    }
    
    @Deprecated
    public void arrangeNodesInCircularLayout(int radius) {
        // place nodes in circle of diameter
        Point centerCircle = new Point(400, 400);
        Vector center = new Vector(-radius, 0);
        double angle = 360D / this.graph.getNodes().size();
        
        for (Node n : this.graph.getNodes()) {
            center.rotate(angle, radius);
            n.x = centerCircle.x + center.x;
            n.y = centerCircle.y + center.y;
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
}
