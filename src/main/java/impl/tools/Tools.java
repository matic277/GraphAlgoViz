package impl.tools;

import java.awt.*;

public class Tools {
    
    public static final Color bgColor = new Color(237, 237, 237, 255);
    public static final Color borderColor = new Color(118, 118, 118);
    
    
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
}
