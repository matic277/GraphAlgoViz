package core;

import impl.Node;
import impl.State;
import impl.tools.Edge;
import impl.tools.Tools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Set;

public interface ComponentDrawer {
    void draw(Graphics2D g, AffineTransform at, Node node);
    
    static ComponentDrawer getIdDrawer() {
        return (g, at, n) -> {
            Font oldFont = g.getFont();
            int fontSize = (int)(at.getScaleY()*12);
            g.setFont(Tools.getBoldFont(fontSize));
//        g.fillRect(ts.getBounds().x-5, ts.getBounds().y, (int)(ts.getBounds().getWidth()/2), (int)(ts.getBounds().getHeight()/2.3));
            g.setColor(Color.black);
            g.drawString(n.id+"", n.id > 9 ? n.ts.getBounds().x - 5: n.ts.getBounds().x, n.ts.getBounds().y);
            g.setFont(oldFont);
        };
    }
    
    static ComponentDrawer getCoordDrawer() {
        return (g, at, n) -> {
            g.setColor(Color.black);
            // position coordinates
            g.drawString(
                    "["+(int)n.ts.getBounds().getCenterX()+", "+(int)n.ts.getBounds().getCenterY()+"]",
                    (int)n.ts.getBounds().getCenterX()-30,
                    (int)(n.ts.getBounds().getCenterY()+n.ts.getBounds().getHeight()/1.2));
        };
    }
    
    static ComponentDrawer getEdgeDrawer(Set<Edge> edges) {
        return (g, at, n) -> {
            g.setColor(Color.black);
            for (Edge e : edges) {
                Node n1 = e.getN1();
                Node n2 = e.getN2();
                g.drawLine((int)n1.ts.getBounds().getCenterX(), (int)n1.ts.getBounds().getCenterY(),
                        (int)n2.ts.getBounds().getCenterX(), (int)n2.ts.getBounds().getCenterY());
            }
        };
    }
    
    static ComponentDrawer getStateDebugDrawer() {
        return (g, at, n) -> {
            g.setColor(Color.black);
            g.drawString(
                    "ST: " + State.stateListToString(n.states) + "",
                    (int)n.ts.getBounds().getCenterX()-30,
                    (int)(n.ts.getBounds().getCenterY()+n.ts.getBounds().getHeight()/1.2));
        };
    }
    
    static ComponentDrawer getNeighborsDrawer() {
        return (g, at, n) -> {
            g.setColor(Color.black);
            g.drawString(
                    "NG: " + Edge.edgesListToString(n.neighbors) + "",
                    (int)n.ts.getBounds().getCenterX()-30,
                    (int)(n.ts.getBounds().getCenterY()+n.ts.getBounds().getHeight()/1.2)+15);
        };
    }
    
    static ComponentDrawer getNullDrawer() {
        return (g, at, n) -> { };
    }
}
