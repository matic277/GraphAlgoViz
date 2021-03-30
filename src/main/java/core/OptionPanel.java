package core;

import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class OptionPanel extends JPanel {
    
    protected Dimension panelSize;
    List<JComponent> components;
    
    public OptionPanel() {
        this.panelSize = new Dimension(400,260);
        this.setVisible(true);
        this.setLayout(null);
        this.setSize(panelSize);
        
        this.components = new ArrayList<>(10);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        // anti-aliasing
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
        g.setColor(Tools.bgColor);
        g.fillRect(0, 0, panelSize.width, panelSize.height);
    
        gr.setColor(Tools.borderColor);
//        g.drawOval(150, 150, 50, 50);
        gr.drawRoundRect(0, 0, panelSize.width-1, panelSize.height-1, 15, 15);
    }
    
    public void setStartingCoordiantes(Point2D point) {
        this.setBounds((int)point.getX(), (int)point.getY(),
                panelSize.width, panelSize.height);
    }
    
    public void addComponents(JComponent... cmps) {
        for (JComponent c : cmps) {
            components.add(c);
            this.add(c);
        }
    }
}
