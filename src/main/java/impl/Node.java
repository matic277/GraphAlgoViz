package impl;

import core.Drawable;
import core.Selectable;
import impl.tools.Tools;

import java.awt.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.awt.geom.Point2D;

public class Node implements Drawable, Selectable {
    
    public int id;
    int rad;
    
    public int x, y;
    
    public List<Node> neighbors;
    
    public double dx, dy;
    
    public int info = 0;
    
    public Deque<Message> msgs;
    
    public Node(int x, int y, int id) {
        this.x = x; this.y = y;
        this.rad = 40;
        this.id = id;
        
        neighbors = new ArrayList<>(5);
    }
    
    static final Color NODE_COLOR_NORMAL = Color.DARK_GRAY;
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(info == 0 ? NODE_COLOR_NORMAL : Color.green);
        g.drawOval(x-rad/2, y-rad/2, rad, rad);
        
        // center
        g.setColor(Color.blue);
        g.fillOval(x-2, y-2, 4, 4);
        
        // pos
        g.setColor(Color.BLACK);
        Font f = g.getFont();
        g.setFont(Tools.getFont(8));
        g.drawString("["+x+","+y+"]", x-15, y-6);
        // id
        g.setFont(Tools.getFont(16));
        g.fillRect(x-6, y+3, 15, 15);
        g.setColor(Color.white);
        g.drawString(id+"", x-3, y+16);
        g.setFont(f);
        
        
//        g.drawLine(x, y, (int)dx, (int)dy);
//        g.drawLine((int)SimulationPanel.mouse.getX(), (int)SimulationPanel.mouse.getY(), x, y);
    }
    
    @Override
    public boolean isSelected(Point2D mouse) {
        // is distance to center equal or less than radius?
        return mouse.distance(x, y) <= rad/2;
    }
    
    @Override
    public void moveTo(Point2D newLocation) {
//        System.out.println("moving"+id);
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
