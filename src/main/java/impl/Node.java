package impl;

import core.Drawable;
import core.Selectable;
import impl.tools.Tools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.awt.geom.Point2D;

public class Node extends Ellipse2D.Double implements Drawable, Selectable {
    
    public int id;
    int rad;
    Shape ts;
    
    public int info = 0;
    public List<Node> neighbors;
    public Deque<Message> msgs;
    
    public Node(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.rad = 45;
        this.width = rad;
        this.height = rad;
        
        this.id = id;
        
        neighbors = new ArrayList<>(5);
    }
    
    static final Color NODE_COLOR_NORMAL = Color.BLACK;
    
    @Override
    public void draw(Graphics2D g, AffineTransform at) {
        ts = at.createTransformedShape(this);
        
        g.setStroke(Tools.BOLD_STROKE);
        g.setColor(Color.black);
        
        // circle & center
        g.draw(ts);
        g.setColor(Color.red);
        g.fillOval((int)ts.getBounds().getCenterX()-3, (int)ts.getBounds().getCenterY()-3, 6, 6);
        
        g.setColor(Color.black);
        // position coordinates
        g.drawString(
                "["+(int)ts.getBounds().getCenterX()+", "+(int)ts.getBounds().getCenterY()+"]",
                (int)ts.getBounds().getCenterX()-30,
                (int)(ts.getBounds().getCenterY()+ts.getBounds().getHeight()));
        
        
        // id in box
        Font oldFont = g.getFont();
        g.setFont(Tools.getBoldFont(16));
        g.fillRect((int)ts.getBounds().getCenterX()-8, (int)ts.getBounds().getCenterY()+3, 17, 17);
        g.setColor(Color.white);
        g.drawString(id+"", (int)ts.getBounds().getCenterX()-4, (int)ts.getBounds().getCenterY()+18);
        g.setFont(oldFont);
    }
    
    // TODO: fix, use ts variable (transformed shape)
    @Override
    public boolean isSelected(Point2D mouse) {
        // is distance to center equal or less than radius?
        return mouse.distance(x, y) <= rad/2;
    }
    
    // TODO: fix, use ts variable (transformed shape) ??
    @Override
    public void moveTo(Point2D newLocation) {
        x = (int)newLocation.getX();
        y = (int)newLocation.getY();
    }
    
    public int getRad() { return rad; }
    
    public void setRad(int rad) { this.rad = rad; }
    
    @Override
    public Point2D getLocation() { return new Point2D.Double(x, y); }
    
    public void receiveMessage(Deque<Message> messages) {
        this.msgs = messages;
    }
}
