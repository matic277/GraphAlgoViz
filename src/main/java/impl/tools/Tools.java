package impl.tools;

import impl.listeners.ButtonListener;

import javax.swing.*;
import java.awt.*;

public class Tools {
    
    public static final Color bgColor = new Color(237, 237, 237, 255);
    public static final Color borderColor = new Color(118, 118, 118);
    
    public static final Dimension menuButtonSize = new Dimension(40, 40);
    
    public static final ButtonListener buttonListener = new ButtonListener();
    
    public static final Stroke PLAIN_STROKE = new BasicStroke(1);
    public static final Stroke BOLD_STROKE = new BasicStroke(1.7f);
    
    public static Font getBoldFont(int size) {
        return new Font("Tahoma", Font.BOLD, size);
    }
    
    public static Font getFont(int size) {
        return new Font("Tahoma", Font.PLAIN, size);
    }
    
    public static void sleep(int ms) {
        try { Thread.sleep(ms); }
        catch (Exception e) { e.printStackTrace(); }
    }
    
    public static JLabel getDumyPlaceholder() {
        JLabel obj = new JLabel(" DUMMY OBJ");
        obj.setForeground(Color.white);
        obj.setPreferredSize(new Dimension(menuButtonSize.width * 3, 20));
        obj.setSize(new Dimension(menuButtonSize.width * 3, 20));
        obj.setOpaque(true);
        obj.setBackground(Color.BLACK);
        return obj;
    }
}
