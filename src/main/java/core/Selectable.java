package core;

import java.awt.geom.Point2D;

public interface Selectable {
    
    boolean isSelected(Point2D mouse);
    void moveTo(Point2D location);
    Point2D getLocation();
    
}
