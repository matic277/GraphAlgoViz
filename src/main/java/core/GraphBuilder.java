package core;

import impl.MyGraph;
import impl.Node;
import impl.tools.Tools;
import impl.tools.Vector;

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
    
    protected String fileName;
    
    public GraphBuilder() { this.graph = new MyGraph(); }
    
    public abstract MyGraph buildGraph();
    
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
    
    public void arrangeNodesInCircularLayout(int radius) {
        // place nodes in circle of diameter
        // x^2 + y^2 = 300^2
        // y = sqrt(300^2 - x^2)
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
    
    // This is slow in some cases
    // TODO optimize when creating nodes in the first place
    public Runnable getNodeInformator() {
        return totalInformed != null ?
                // inform number of nodes (randomly)
                () -> {
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
                    this.graph.getNodes().forEach(n ->
                            n.getState().setState(
                                    Tools.RAND.nextInt(100) <= informedProbability ? 1 : 0));
                };
    }
}
