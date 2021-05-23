package impl.panels.tabs;

import impl.MyGraph;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class ColorTab extends JPanel {
    
    TabsPanel parent;
    
    JButton openBtn;
    
    MyGraph graph;
    
    JFrame colorFrame;
    
    public ColorTab(TabsPanel parent) {
        this.parent = parent;
        this.graph = MyGraph.getInstance();
        
        this.setOpaque(true);
        
        // Oracles JColorChooser is broken
        openBtn = new JButton("Open colorpicker");
        openBtn.addActionListener(a -> SwingUtilities.invokeLater(() -> {
            colorFrame = new JFrame();
            colorFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JColorChooser color = new JColorChooser();
            colorFrame.add(color);
            colorFrame.pack();
            colorFrame.setVisible(true);
        }));
        
        
        this.add(openBtn);
        
        // JColorChooser is broken ?
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Tools.sleep(1000/60);
        super.paintComponent(g);
    }
    
}
