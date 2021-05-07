package impl.tools;

import impl.listeners.ButtonListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;

public class Tools {
    
    public static final Color bgColor = Color.white;
    public static final Color borderColor = new Color(118, 118, 118);
    public static final Color RED = Color.decode("#ea4335");
    public static final Color GRAY = Color.decode("#BFBDBD");
    public static final Color MEUN_COLORS = Color.decode("#E0DDDD");
    
    public static final Dimension MENU_BUTTON_SIZE = new Dimension(40, 40);
    public static final Dimension MENU_BUTTON_SIZE_WIDE = new Dimension(100, 40);
    public static final Dimension MENU_CHECKBOX_SIZE = new Dimension(125, 15);
    
    public static final ButtonListener buttonListener = new ButtonListener();
    
    public static final Stroke PLAIN_STROKE = new BasicStroke(1);
    public static final Stroke BOLD_STROKE = new BasicStroke(1.7f);
    public static final Stroke BOLDER_STROKE = new BasicStroke(2f);
    public static final Stroke BOLDEST_STROKE = new BasicStroke(7f);
    
    public static final int INITIAL_WINDOW_WIDTH = 1400;
    public static final int INITIAL_WINDOW_HEIGHT = 1000;
    public static final int INITIAL_LEFT_MENU_WIDTH = 150;
    public static final int INITIAL_BOTTOM_MENU_HEIGHT = 200;
    public static final int INITIAL_STATS_PANEL_WIDTH = 150;
    
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
    
    public static class RoundBorder implements Border {
        Color clr;
        private final int rad;
        Stroke stroke;
        public RoundBorder(Color clr, Stroke stroke, int rad) { this.rad = rad; this.clr = clr; this.stroke = stroke; }
        
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(clr);
            ((Graphics2D) g).setStroke(stroke);
            g.drawRoundRect(x+1, y+1, width-3, height-3, rad, rad);
        }
        public boolean isBorderOpaque() { return true; }
        public Insets getBorderInsets(Component c) { return new Insets(this.rad+1, this.rad+1, this.rad+2, this.rad); }
    }
}
