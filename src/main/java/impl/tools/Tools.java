package impl.tools;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tools {
    
    public static final Color bgColor = Color.white;
    public static final Color borderColor = new Color(213, 216, 222);
    public static final Color RED = Color.decode("#ea4335");
    public static final Color GRAY = Color.decode("#D5D8DE");
    public static final Color GRAY2 = Color.decode("#E0E0E0");
    public static final Color GRAY3 = new Color(243, 243, 243);
    public static final Color GREEN = Color.decode("#34A853");
    public static final Color BACKGROUND_RED = new Color(255, 242, 242);
    public static final Color BACKGROUND_GREEN = new Color(236, 255, 236);
    public static final Color LIGHT_GRAY = new Color(213, 216, 222);
    public static final Color MEUN_COLORS = Color.decode("#E0DDDD");
    
    public static final Dimension MENU_BUTTON_SIZE = new Dimension(53, 37);
    public static final Dimension MENU_BUTTON_SIZE_WIDE = new Dimension(110, 37);
    public static final Dimension MENU_CHECKBOX_SIZE = new Dimension(125, 15);
    
    public static final Stroke PLAIN_STROKE = new BasicStroke(1);
    public static final Stroke BOLD_STROKE = new BasicStroke(1.7f);
    public static final Stroke BOLDER_STROKE = new BasicStroke(2f);
    public static final Stroke BOLDEST_STROKE = new BasicStroke(7f);
    
    public static final int INITIAL_WINDOW_WIDTH = 1400;
    public static final int INITIAL_WINDOW_HEIGHT = 1000;
    public static final int INITIAL_LEFT_MENU_WIDTH = 220;
    public static final int MAXIMUM_LEFT_MENU_WIDTH = 220;
    public static final int MAXIMUM_STATS_PANEL_WIDTH = 345;
    public static final int INITIAL_BOTTOM_MENU_HEIGHT = 200;
    public static final int INITIAL_STATS_PANEL_WIDTH = MAXIMUM_STATS_PANEL_WIDTH;
    public static final Dimension INITIAL_IMPORT_WINDOW_SIZE = new Dimension(500, 550);
    
    public static final Random RAND = new Random();
    
    // Store request fonts in a map.
    // So when multiple components request a font of size X
    // just make a lookup and return it. Otherwise create
    // it for yourself and others.
    public static final Map<Integer, Font> regularFontMap;
    public static final Map<Integer, Font> boldFontMap;
    public static final Map<Integer, Font> monospacedFontMap;
    static {
        regularFontMap = new HashMap<>();
        boldFontMap = new HashMap<>();
        monospacedFontMap = new HashMap<>();
    }
    
    public synchronized static Font getBoldFont(int size) {
        return boldFontMap.computeIfAbsent(size, k -> new Font("Source sans pro bold", Font.BOLD, size));
    }
    
    public synchronized static Font getFont(int size) {
        return regularFontMap.computeIfAbsent(size, k -> new Font("Source sans pro", Font.PLAIN, size));
    }
    
    public synchronized static Font getMonospacedFont(int size) {
        return monospacedFontMap.computeIfAbsent(size, k -> new Font("Roboto mono thin", Font.PLAIN, size));
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
        JLabel obj = new JLabel(" DUMMY SEPARATOR");
        obj.setForeground(Color.white);
        obj.setPreferredSize(new Dimension((int)(MENU_BUTTON_SIZE.width * 3.5), 20));
        obj.setSize(new Dimension((int)(MENU_BUTTON_SIZE.width * 3.5), 20));
        obj.setOpaque(true);
        obj.setBackground(Color.BLACK);
        //obj.setAlignmentX(Component.CENTER_ALIGNMENT);
        return obj;
    }
    
    public static class RoundBorder implements Border {
        private final Color clr;
        private final int rad;
        private final Stroke stroke;
        public RoundBorder(Color clr, Stroke stroke, int rad) { this.rad = rad; this.clr = clr; this.stroke = stroke; }
        
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            // anti-aliasing
            Graphics2D gr = (Graphics2D) g;
            gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            gr.setColor(clr);
            gr.setStroke(stroke);
            gr.drawRoundRect(x+1, y+1, width-3, height-3, rad, rad);
        }
        public boolean isBorderOpaque() { return true; }
        public Insets getBorderInsets(Component c) { return new Insets(this.rad+1, this.rad+1, this.rad+2, this.rad); }
    }
}
