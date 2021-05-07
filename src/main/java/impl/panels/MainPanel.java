package impl.panels;

import impl.windows.SimulationWindow;

import javax.swing.*;

public class MainPanel extends JSplitPane {
    
    // contains vertically split panels:
    //   middlePanel(menuPanel, simulationPanel)
    MiddlePanel middlePanel;
    
    // contains vertically split panels:
    //  bottomPanel(statsPanel, historyTabPanel)
    BottomPanel bottomPanel;
    
    
    SimulationWindow parent;
    
    // Splits middlePanel and bottomPanel horizontally
    public MainPanel(SimulationWindow parent) {
        super(VERTICAL_SPLIT, null, null);
        this.parent = parent;
        
        this.setSize(parent.getWindowSize());
        this.setPreferredSize(parent.getWindowSize());
        
        this.middlePanel = new MiddlePanel(this);
        this.bottomPanel = new BottomPanel(this, parent.getSimulationManager().getGraph());
        
        this.setLeftComponent(middlePanel);
        this.setRightComponent(bottomPanel);
//        super.getOrientation();
    }
    
    public MiddlePanel getMiddlePanel() { return this.middlePanel; }
    
    public BottomPanel getBottomPanel() { return this.bottomPanel; }
    
    public SimulationWindow getSimulationWindow() { return this.parent; }
}
