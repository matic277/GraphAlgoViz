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
    public Shape ts;
    
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
        
        this.ts = this;
        
        neighbors = new ArrayList<>(5);
    }
    
    @Override
    public void draw(Graphics2D g, AffineTransform at) {
        ts = at.createTransformedShape(this);
        
        g.setStroke(Tools.BOLD_STROKE);
        g.setColor(info == 0 ? Color.black : Color.green);
        
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
        System.out.println("moved to:" + x + ", " +y);
//        ts.getBounds().x = x;
//        ts.getBounds().y = y;
        
        this.x = x;
        this.y = y;
    }
    
    public int getRad() { return rad; }
    
    public void setRad(int rad) { this.rad = rad; }
    
    @Override
    public Point2D getLocation() { return new Point2D.Double(x, y); }
    
    public void receiveMessage(Deque<Message> messages) {
        this.msgs = messages;
    }
}
