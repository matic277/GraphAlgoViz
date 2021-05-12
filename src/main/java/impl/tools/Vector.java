package impl.tools;

import java.awt.*;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Class taken from another project,
 * only used for rotating a vector when
 * generating coordinates that places
 * nodes in a circle (decent layout for clique graphs).
 */
public class Vector {
    
    public double x;
    public double y;
    
    DecimalFormat rounder = new DecimalFormat("#.#");
    
    public Vector() {
        x = (Tools.RAND.nextBoolean())? Tools.RAND.nextDouble() : -Tools.RAND.nextDouble();
        y = (Tools.RAND.nextBoolean())? Tools.RAND.nextDouble() : -Tools.RAND.nextDouble();
        
        this.norm();
    }
    public Vector(double x_, double y_) {
        x = x_;
        y = y_;
    }
    public Vector(Vector v) {
        x = v.x; y = v.y;
    }
    
    public void set(Vector v) { x = v.x; y = v.y; }
    public void set(Point p) { x = p.x; y = p.y; }
    public void set(int x_, int y_) { x = x_; y = y_; }
    public void set(double x_, double y_) { x = x_; y = y_; }
    
    public void rotate(double angle, double len) {
        if (angle == 0) return;
        
        x = x * Math.cos(Math.toRadians(angle)) - y * Math.sin(Math.toRadians(angle));
        y = x * Math.sin(Math.toRadians(angle)) + y * Math.cos(Math.toRadians(angle));
        
        // we have a vector of wanted size
        // (has already been normalized and scaled(multiplied))
        // but if we don't scale it again after rotation, the
        // margin of errors (because of type double) start
        // adding up, and vectors get shorter and shorter
        // as sequence progresses
        this.norm();
        this.multi(len);
    }
    
    public void add(Vector v) {
        x += v.x;
        y += v.y;
    }
    
    public void sub(Vector v) {
        x -= v.x;
        y -= v.y;
    }
    
    public void multi(double n) {
        x *= n;
        y *= n;
    }
    
    public void div(double n) {
        x /= n;
        y /= n;
    }
    
    public double mag() {
        return Math.sqrt(x*x + y*y);
    }
    
    public void norm() {
        double v_mag = mag();
        if (v_mag > 0) {
            div(v_mag);
        }
    }
    
    public static void draw(Graphics2D g, Vector v, Color c) {
        g.setColor(c);
        g.drawLine(300, 300, (int)v.x+100, (int)v.y+100);
    }
    
    public static void draw(Graphics2D g, Vector v, Color c, Point2D startingCoords) {
        g.setColor(c);
        if (startingCoords != null)
            g.drawLine((int)startingCoords.getX(), (int)startingCoords.getY(), (int)v.x, (int)v.y);
        else
            g.drawLine(300, 300, (int)v.x+100, (int)v.y+100);
    }
    
    public static boolean compare(Vector v1, Vector v2) {
        if (v1.x == v2.x && v1.y == v2.y) return true;
        return false;
    }
    
    public String toString() {
        return "(" + rounder.format(x) + ", " + rounder.format(y) + ")";
    }
}
