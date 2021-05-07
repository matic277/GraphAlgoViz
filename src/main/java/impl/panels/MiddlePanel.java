package impl.panels;

import impl.tools.Tools;
import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.*;

public class MiddlePanel extends JSplitPane {
    
    MainPanel parent;
    
    SimulationPanel simPanel;
    MenuPanel menuPanel;
    
    public MiddlePanel(MainPanel parent) {
        this.parent = parent;
        
        Dimension panelSize = new Dimension(parent.getWidth(), parent.getHeight() - Tools.INITIAL_BOTTOM_MENU_HEIGHT);
        System.out.println("size=" + panelSize);
        this.setSize(panelSize);
        this.setPreferredSize(panelSize);
        
        menuPanel = new MenuPanel(this, simPanel);
        simPanel = new SimulationPanel(this, parent.getSimulationWindow().getSimulationManager().getGraph());
        this.setLeftComponent(menuPanel);
        this.setRightComponent(simPanel);
    }
    
    public SimulationWindow getSimulationWindow() { return this.parent.getSimulationWindow(); }
    
    public SimulationPanel getSimulationPanel() { return this.simPanel; }
    
    public MainPanel getMainPanel() { return this.parent; }
}
