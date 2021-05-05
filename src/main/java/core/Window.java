package core;

import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;

public abstract class Window {
    
    protected JFrame frame;
    protected JPanel panel;
    
    protected Dimension windowSize;
    
    public Window(Dimension windowSize) {
        this.windowSize = windowSize;
        
        frame = new JFrame();
//        frame.setSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setLayout(null);
        frame.setVisible(true);
        
        // TODO move this to startup window
        // draw round rectangle for text
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D gr = (Graphics2D) g;
                // anti-aliasing
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                gr.setStroke(new BasicStroke(2f));
                g.setColor(Tools.bgColor);
                g.fillRect(0, 0, getWidth(), getHeight());
                
                gr.setColor(Color.BLACK);
                gr.fillRoundRect(81, 110, 337, 50, 12, 12);
                gr.drawRoundRect(81, 55, 337, 102, 12, 12);
                gr.fillRect(81, 101, 337, 20);
                
                Tools.sleep(1000/60);
                super.repaint();
            }};
        
        panel.setOpaque(true);
        panel.setBackground(Tools.bgColor);
        panel.setSize(windowSize);
        panel.setPreferredSize(windowSize);
//        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setLayout(null);
        panel.setVisible(true);
        
    
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {e.printStackTrace();}
        
        frame.add(panel);
        frame.pack();
    }
    
    public Dimension getWindowSize() {
        return this.windowSize;
    }
}
