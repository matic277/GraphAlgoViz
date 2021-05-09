package impl.panels;

import impl.MyGraph;
import impl.NullGraph;
import impl.tools.Tools;
import impl.windows.SimulationWindow;

import javax.swing.*;

public class MainPanel extends JSplitPane {
    
    SimulationWindow parent;
    
    // contains vertically split panels:
    //   topPanel(menuPanel, simulationPanel)
    TopPanel topPanel;
    
    // contains vertically split panels:
    //  bottomPanel(statsPanel, historyTabPanel)
    BottomPanel bottomPanel;
    
    
    // Splits middlePanel and bottomPanel horizontally
    public MainPanel(SimulationWindow parent) {
        super(VERTICAL_SPLIT, null, null);
        this.parent = parent;
        
        this.setSize(parent.getWindowSize());
        this.setPreferredSize(parent.getWindowSize());
        
        this.topPanel = new TopPanel(this);
        this.bottomPanel = new BottomPanel(this);
        
        this.setLeftComponent(topPanel);
        this.setRightComponent(bottomPanel);
        
        // set location of divider, so that height
        // of bottom menu is set to initial state
        this.setDividerLocation(Tools.INITIAL_WINDOW_HEIGHT - Tools.INITIAL_BOTTOM_MENU_HEIGHT);
        
        // set this to 1, so that middlePanel (simPanel and mainButton)
        // gain height, and not bottom component (bottomPanel)
        this.setResizeWeight(1);
    }
    
    public TopPanel getTopPanel() { return this.topPanel; }
    
    public BottomPanel getBottomPanel() { return this.bottomPanel; }
    
    public SimulationWindow getSimulationWindow() { return this.parent; }
    
    public void setNewGraph(MyGraph graph) {
        topPanel.setNewGraph(graph);
        bottomPanel.setNewGraph(graph);
    }
}
