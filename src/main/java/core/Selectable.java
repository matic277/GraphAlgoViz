package core;

import java.awt.geom.Point2D;

public interface Selectable {
    
    boolean isSelected(Point2D mouse);
    void moveTo(int x, int y);
    Point2D getLocation();
    
}
