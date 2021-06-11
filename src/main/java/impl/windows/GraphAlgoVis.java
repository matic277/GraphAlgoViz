package impl.windows;

import com.formdev.flatlaf.FlatLightLaf;
import core.Algorithm;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.File;

public class GraphAlgoVis {
    
    // singleton
    private static GraphAlgoVis INSTANCE;
    
    public static synchronized GraphAlgoVis get(Algorithm algorithm) {
        INSTANCE = new GraphAlgoVis(algorithm);
        return INSTANCE;
    }
    
    private final Algorithm algorithm;
    
    private GraphAlgoVis(Algorithm algorithm) {
        this.algorithm = algorithm;
        
        init();
    }
    
    public void openWindow() {
        SwingUtilities.invokeLater(() -> new SimulationWindow(algorithm));
    }
    
    private void init() {
        try {
            // Roboto Mono SemiBold
            // Roboto Mono Medium
            // Roboto Mono Regular
            // Roboto Mono Thin
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/RobotoMono-SemiBold.ttf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/RobotoMono-Medium.ttf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/RobotoMono-Regular.ttf")));
        
            // Source Sans Pro
            // Source Sans Pro Bold
            // Source Sans Pro Light
            // Source Sans Pro Semibold
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/SourceSansPro-Light.ttf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/SourceSansPro-Regular.ttf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/SourceSansPro-SemiBold.ttf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/SourceSansPro-Bold.ttf")));
        } catch (Exception e) {
            //Handle exception
            e.printStackTrace();
        }
        
        // Look and feed
        FlatLightLaf.setup();
        UIManager.put("Button.arc", 10 );
        UIManager.put("Component.arc", 30 );
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("TextComponent.arc", 50 );
        
        try { UIManager.setLookAndFeel( new FlatLightLaf() ); } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }
}
