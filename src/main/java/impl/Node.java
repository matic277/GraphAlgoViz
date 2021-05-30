package impl;

import core.ComponentDrawer;
import core.Drawable;
import core.Selectable;
import impl.tools.Tools;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jgrapht.Graphs;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Point2D;

public class Node extends Ellipse2D.Double implements Drawable, Selectable {
    
    public int id;
    public Shape ts;
    
    @Deprecated
    public static int rad = 45;
    
    public int info = 0;
//    public Set<Node> neighbors;
    
    public List<State> states;
    int messagesReceived = 0;
    int messagesSent = 0;
    
    public static Color INFORMED_COLOR = Tools.GREEN;
    public static Color UNINFORMED_COLOR = Color.black;
    
    public static ComponentDrawer idDrawer = ComponentDrawer.getNullDrawer();
    public static ComponentDrawer coordDrawer = ComponentDrawer.getNullDrawer();
    public static ComponentDrawer stateDebugDrawer = ComponentDrawer.getNullDrawer();
    public static ComponentDrawer neighborsDrawer = ComponentDrawer.getNullDrawer();
    
    @Deprecated(since = "Do not use this constructor, use MyGraph.newNode()")
    public Node(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.width = rad;
        this.height = rad;
        
        this.id = id;
        
        this.ts = this;

        states = new ArrayList<>(10);
        states.add(new State(0)); // uninformed on initialize
    }

    @Override
    public void draw(Graphics2D g, AffineTransform at) {
        // TODO: nodes don't scale in-place on slider change

        ts = at.createTransformedShape(this);

        g.setColor(states.get(AlgorithmController.currentStateIndex).getState() == 0 ? UNINFORMED_COLOR : INFORMED_COLOR);

        // node (circle)
        g.fill(ts);

        idDrawer.draw(g, at, this);
        coordDrawer.draw(g, at, this);
        stateDebugDrawer.draw(g, at, this);
        neighborsDrawer.draw(g, at, this);
    }

    @Override
    public boolean isSelected(Point2D mouse) {
        return ts.contains(mouse);
    }

    // TODO: fix, use ts variable (transformed shape) ??
    @Override
    public void moveTo(int x, int y) {
//        System.out.println("moved to:" + x + ", " +y);
//        ts.getBounds().x = x;
//        ts.getBounds().y = y;
        this.x = x;
        this.y = y;
    }

    @Override
    public Point2D getLocation() { return new Point2D.Double(x, y); }

    @Override
    public String toString() {
        // do not call getNeighbors() in here!
        // produces StackOverflow when node is deleted
        return "[N="+id+"]{"+"}";
    }

    public State getState() { return this.states.get(AlgorithmController.currentStateIndex); }

    public void addState(State state) { this.states.add(state); }

    public int getId() { return this.id; }

    // equals & hashcode needed due to Node mutation
    // in order to make sure hashset operations work
    @Override
    public boolean equals(Object o) {
        if((o == null) || (o.getClass() != this.getClass())) {
            return false;
        }
        return o == this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
                .toHashCode();
    }

    public List<Node> getNeighbors() {
        return Graphs.neighborListOf(MyGraph.getInstance().getGraph(), this);
//        return MyGraph.getInstance().getGraph().getEdgeTarget(MyGraph.getInstance().getGraph().edgesOf(this));
    }
    
    public void setPosition(org.jgrapht.alg.drawing.model.Point2D p) {
        this.x = p.getX(); this.y = p.getY();
    }
}
