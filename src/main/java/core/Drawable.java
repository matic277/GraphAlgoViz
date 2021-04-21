package core;

import java.awt.*;
import java.awt.geom.AffineTransform;

public interface Drawable {
    
    public void draw(Graphics2D g, AffineTransform at);
}
