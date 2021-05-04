package impl.tools;

import impl.listeners.ButtonListener;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Tools {
    
    public static final Color bgColor = new Color(245, 245, 245, 245);
    public static final Color borderColor = new Color(118, 118, 118);
    public static final Color RED = Color.decode("#ea4335");
    
    public static final Dimension MENU_BUTTON_SIZE = new Dimension(40, 40);
    public static final Dimension MENU_BUTTON_SIZE_WIDE = new Dimension(100, 40);
    public static final Dimension MENU_CHECKBOX_SIZE = new Dimension(125, 15);
    
    public static final ButtonListener buttonListener = new ButtonListener();
    
    public static final Stroke PLAIN_STROKE = new BasicStroke(1);
    public static final Stroke BOLD_STROKE = new BasicStroke(1.7f);
    public static final Stroke BOLDER_STROKE = new BasicStroke(2f);
    public static final Stroke BOLDEST_STROKE = new BasicStroke(5f);
    
    
    public static final Random RAND = new Random();
    
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
    
    public static void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (Exception e) { e.printStackTrace(); }
    }
    
    public static JLabel getDumyPlaceholder() {
        JLabel obj = new JLabel(" DUMMY OBJ");
        obj.setForeground(Color.white);
        obj.setPreferredSize(new Dimension(MENU_BUTTON_SIZE.width * 3, 20));
        obj.setSize(new Dimension(MENU_BUTTON_SIZE.width * 3, 20));
        obj.setOpaque(true);
        obj.setBackground(Color.BLACK);
        return obj;
    }
}
