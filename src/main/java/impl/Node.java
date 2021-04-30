package impl;

import core.Drawable;
import core.Selectable;
import impl.tools.Tools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Point2D;

public class Node extends Ellipse2D.Double implements Drawable, Selectable {
    
    public int id;
    public static int rad = 45;
    public Shape ts;
    
    public int info = 0;
    public List<Node> neighbors;
    
    List<State> states;
    public int currentStateIndex = 0;
    int messagesReceived = 0;
    int messagesSent = 0;
    
    static final Color INFORMED = Color.decode("#34A853");
    static final Color UNINFORMED = Color.black;
    
    public Node(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.width = rad;
        this.height = rad;
        
        this.id = id;
        
        this.ts = this;
        
        neighbors = new ArrayList<>(5);
        states = new ArrayList<>(100);
        
        // TODO
        if (id == 0 || id == 8) {
            states.add(new State(1));
        } else {
            states.add(new State(0));
        }
    }
    
//    public void setState(State newState) {
//        this.state = newState;
//    }
    public State getState() {
        messagesSent++;
        return this.states.get(currentStateIndex);
    }
    public void addState(State state) {
        this.states.add(state);
//        currentStateIndex++;
    }
    public void incrementToNextState() {
        currentStateIndex++;
    }
    
    @Override
    public void draw(Graphics2D g, AffineTransform at) {
        // TODO: nodes don't scale in-place on slider change
        this.height = rad;
        this.width = rad;
        
        ts = at.createTransformedShape(this);
        
        g.setStroke(Tools.BOLDEST_STROKE);
        g.setColor(states.get(currentStateIndex).getState() == 0 ? UNINFORMED : INFORMED);
        
        // circle & center
        g.draw(ts);
        g.setColor(Color.red);
        g.fillOval((int)ts.getBounds().getCenterX()-3, (int)ts.getBounds().getCenterY()-3, 6, 6);
        
        g.setStroke(Tools.BOLD_STROKE);
        
        g.setColor(Color.black);
        // position coordinates
        g.drawString(
                "["+(int)ts.getBounds().getCenterX()+", "+(int)ts.getBounds().getCenterY()+"]",
                (int)ts.getBounds().getCenterX()-30,
                (int)(ts.getBounds().getCenterY()+ts.getBounds().getHeight()/1.2));
//        g.drawString(
//                "["+(int)this.getBounds().getCenterX()+", "+(int)this.getBounds().getCenterY()+"]",
//                (int)this.getBounds().getCenterX()-30,
//                (int)(this.getBounds().getCenterY()+this.getBounds().getHeight()+20));
        
        // id in box
        Font oldFont = g.getFont();
        g.setFont(Tools.getBoldFont((int)(at.getScaleY()*16)));
        g.fillRect((int)ts.getBounds().getCenterX()-(int)ts.getBounds().getWidth()/4, (int)ts.getBounds().getCenterY()+2, (int)(ts.getBounds().getWidth()/2), (int)(ts.getBounds().getHeight()/2.3));
        g.setColor(Color.white);
        g.drawString(id+"", (int)ts.getBounds().getCenterX()-(int)ts.getBounds().getWidth()/8, (int)(ts.getBounds().getCenterY()+ts.getBounds().getHeight()/2.5)+2);
        g.setFont(oldFont);
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
        return "[N="+id+"]";
    }
    
    public int getId() { return this.id; }
}
