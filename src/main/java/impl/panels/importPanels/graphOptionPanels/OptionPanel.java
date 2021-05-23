package impl.panels.importPanels.graphOptionPanels;

import core.GraphType;
import impl.tools.Tools;
import impl.windows.ImportGraphWindow;
import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class OptionPanel extends JPanel {
    
    protected ImportGraphWindow parent;
    protected SimulationWindow simWindow;
    
    protected Dimension panelSize;
    List<JComponent> components;
    
    public OptionPanel(ImportGraphWindow parent) {
        this.parent = parent;
//        this.panelSize = new Dimension(300,200);
        this.setVisible(true);
        this.setPreferredSize(panelSize);
        this.setMaximumSize(panelSize);
        
        this.setOpaque(true);
        this.setBackground(Tools.bgColor);
        
        this.components = new ArrayList<>(10);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D gr = (Graphics2D) g;
        // anti-aliasing
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        g.setColor(Tools.GRAY3);
        gr.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        
        gr.setColor(Tools.GRAY);
        gr.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    
    public void addComponents(JComponent... cmps) {
        for (JComponent c : cmps) {
            components.add(c);
            this.add(c);
        }
    }
    
    public void setSimulationWindow(SimulationWindow simWindow) {
        this.simWindow = simWindow;
    }
    
    public abstract ActionListener getButtonAction(GraphType type);
}
