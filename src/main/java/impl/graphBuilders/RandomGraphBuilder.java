package impl.graphBuilders;

import core.GraphBuilder;
import impl.MyGraph;
import impl.Node;
import impl.tools.Vector;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;

public class RandomGraphBuilder extends GraphBuilder {
    
    public RandomGraphBuilder() {
        super();
    }
    
    
    /**
     * Each edge in graph exists with given probability.
     * @return
     */
    @Override
    public MyGraph buildGraph() {
        System.out.println("building");
        // generate nodes
        for (int i=0; i<this.totalNodes; i++) {
            graph.addNode(new Node(i, -i,i));
        }
    
        System.out.println("nodes:  " + totalNodes);
        System.out.println("nodes1: " + this.graph.getNodes().size());
        
        // generate edges given probability
        Random r = new Random();
        
        for (Node n1 : graph.getNodes()) {
            for (Node n2 : graph.getNodes()) {
                if (n1 == n2) continue;
                if (r.nextDouble() <= this.edgeProbability) {
                    this.graph.addEdge(n1, n2);
                }
            }
        }
        
        // TODO
        // place nodes in (10 x INF) grid
//        Iterator<Node> iter = this.graph.getNodes().iterator();
//        int dx = 100, dy = 100;
//        for (int y=50, i=0; iter.hasNext(); y+=dy, i++) {
//            for (int x=50, j=0; j<10 && iter.hasNext(); x+=dx, j++) {
//                Node n = iter.next();
//                n.x = x; n.y = y;
//            }
//        }
        
        // TODO
        // place nodes in circle of diameter 300px
        // x^2 + y^2 = 300^2
        // y = sqrt(300^2 - x^2)
        final int rad = 300;
        Point centerCircle = new Point(400, 400);
        Vector center = new Vector(-rad, 0);
        double angle = 360 / this.graph.getNodes().size();
        
        for (Node n : this.graph.getNodes()) {
            center.rotate(angle, rad);
            n.x = centerCircle.x + center.x;
            n.y = centerCircle.y + center.y;
        }
        
        
        return this.graph;
    }
    
}
