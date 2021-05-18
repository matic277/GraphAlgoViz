package impl.panels;

import impl.MyGraph;
import impl.tools.Tools;
import impl.windows.SimulationWindow;

import javax.swing.*;

public class TopPanel extends JSplitPane {
    
    MainPanel parent;
    
    SimulationPanel simPanel;
    MenuPanel menuPanel;
    
    public TopPanel(MainPanel parent) {
        this.parent = parent;
        
        simPanel = new SimulationPanel(this);
        menuPanel = new MenuPanel(this);
        this.setLeftComponent(menuPanel);
        this.setRightComponent(simPanel);
        
        // set location of divider, so that width
        // of left menu is set to initial state
        this.setDividerLocation(Tools.INITIAL_LEFT_MENU_WIDTH);
        this.setResizeWeight(0);
    }
    
    public SimulationWindow getSimulationWindow() { return this.parent.getSimulationWindow(); }
    
    public SimulationPanel getSimulationPanel() { return this.simPanel; }
    
    public MainPanel getMainPanel() { return this.parent; }
    
    public void onNewGraphImport() {
        simPanel.onNewGraphImport();
        menuPanel.onNewGraphImport();
    }
}
