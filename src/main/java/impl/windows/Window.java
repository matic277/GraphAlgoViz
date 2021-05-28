package impl.windows;

import impl.tools.Tools;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.util.Arrays;

public abstract class Window {
    
    protected JFrame frame;
    protected JComponent mainComponent;
    
    protected Dimension windowSize;
    
    public Window(Dimension windowSize) {
        this.windowSize = windowSize;
        
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        try {
            System.out.println(Arrays.toString(Arrays.stream(UIManager.getInstalledLookAndFeels()).map(UIManager.LookAndFeelInfo::getName).toArray()));
            //System.out.println(Arrays.toString(Arrays.stream(UIManager.getAuxiliaryLookAndFeels()).map(LookAndFeel::getName).toArray()));
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//            UIManager.getLookAndFeelDefaults().put(
//                    "TabbedPane:TabbedPaneTabArea[Disabled].backgroundPainter", null);
//            UIManager.getLookAndFeelDefaults().put(
//                    "TabbedPane:TabbedPaneTabArea[Enabled+MouseOver].backgroundPainter", null);
//            UIManager.getLookAndFeelDefaults().put(
//                    "TabbedPane:TabbedPaneTabArea[Enabled+Pressed].backgroundPainter", null);
//            UIManager.getLookAndFeelDefaults().put(
//                    "TabbedPane:TabbedPaneTabArea[Enabled].backgroundPainter", null);
            for ( var x : UIManager.getLookAndFeelDefaults().keySet()) {
                if (x.toString().toLowerCase().contains("tabbedpane"))
                    System.out.println(x);
            }

        } catch (Exception e) {e.printStackTrace();}
    }
    
    public void addMainComponent(JComponent cmp) {
        if (mainComponent != null) throw new RuntimeException("Main component already set!");
        this.mainComponent = cmp;
        this.frame.add(cmp);
        this.frame.pack();
    }
    
    public Dimension getInitialWindowSize() {
        return this.windowSize;
    }
    
    public Dimension getWindowSize() {
        return this.frame.getSize();
    }
}
