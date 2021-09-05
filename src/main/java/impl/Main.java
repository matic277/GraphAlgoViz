package impl;

import com.formdev.flatlaf.FlatDarculaLaf;

import core.Algorithm;
import impl.tools.Tools;
import impl.windows.SimulationWindow;


import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
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
        
//        try {
//            System.out.println(Arrays.toString(Arrays.stream(UIManager.getInstalledLookAndFeels()).map(UIManager.LookAndFeelInfo::getName).toArray()));
//            //System.out.println(Arrays.toString(Arrays.stream(UIManager.getAuxiliaryLookAndFeels()).map(LookAndFeel::getName).toArray()));
//            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        } catch (Exception e) {e.printStackTrace();}
        
        // Look and feed
        FlatDarculaLaf.setup();
        UIManager.put("Button.arc", 10 );
        UIManager.put("Component.arc", 30 );
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("TextComponent.arc", 50 );
        
        // scroll bars
//        UIManager.put( "ScrollBar.thumbArc", 999 );
//        UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
//        UIManager.put( "TextComponent.arc", 30 );
        
        try { UIManager.setLookAndFeel( new FlatDarculaLaf() ); } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    
//        UIManager.getLookAndFeel().getDefaults().forEach((a, b) -> {
//            System.out.println(a.toString());
//            System.out.println(b.toString());
//        });
        
        
        // Change nimbus coloring to lighter
        //UIManager.put("nimbusBase", new ColorUIResource(150, 150, 150));
        
//        SwingUtilities.invokeLater(ImportGraphWindow::new);
        SwingUtilities.invokeLater(() -> new SimulationWindow(getAlgorithm()));
    }
    
    public static Algorithm getAlgorithm() {
        return vertex -> {
            // if you have info, don't do anything
            if (vertex.getState().info > 0) return new State(vertex.getState().info);
            
            // Some nodes have no neighbors, so
            // in this case don't do anything.
            // Return the same state you're in.
            if (vertex.getNeighbors().isEmpty()) return new State(vertex.getState().info);
            
            // get two random neighbors
            Node randNeigh1 = vertex.getNeighbors().get(Tools.RAND.nextInt(vertex.getNeighbors().size()));
            Node randNeigh2 = vertex.getNeighbors().get(Tools.RAND.nextInt(vertex.getNeighbors().size()));
            State stateOfNeigh1 = randNeigh1.getState();
            State stateOfNeigh2 = randNeigh2.getState();
            
            // or
            int newStateInfo = stateOfNeigh1.info | stateOfNeigh2.info | vertex.getState().info;
            
            return new State(newStateInfo);
        };
    }
}
