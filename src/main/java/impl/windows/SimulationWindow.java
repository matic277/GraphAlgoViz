package impl.windows;

import core.Window;
import impl.AlgorithmController;
import impl.MyGraph;
import impl.SimulationManager;
import impl.panels.*;

import java.awt.*;

public class SimulationWindow extends Window {
    
    SimulationManager manager;
    
//    SimulationPanel simPanel;
//    MenuPanel menuPanel;
//
//    MiddlePanel middlePanel; // contains 2 sub-panels, resizable (menu, simpanel)
//    BottomPanel bottomPanel; // contains 2 sub-panels, resizable
    
    MainPanel mainPanel;
    
    final int menuWidth = 150;
    
    public SimulationWindow(Dimension windowSize, MyGraph g, SimulationManager parent) {
        super(windowSize);
        this.frame.remove(this.panel);
        this.frame.setLayout(new BorderLayout());
        this.manager = parent;
        
        Dimension menuSize = new Dimension(menuWidth, windowSize.height);
        Dimension simSize = new Dimension(windowSize.width-menuWidth, windowSize.height);
        
//        this.simPanel = new SimulationPanel(this, g, simSize);
//        this.menuPanel = new MenuPanel(this, simPanel, menuSize);
//        this.middlePanel = new MiddlePanel(this);
//        this.bottomPanel = new BottomPanel(this, g, 200);
        mainPanel = new MainPanel(this);
        
//        this.frame.add(menuPanel, BorderLayout.WEST);
        this.frame.add(mainPanel, BorderLayout.CENTER);
//        this.frame.add(bottomPanel, BorderLayout.SOUTH);
        
        this.frame.pack();
    }
    
    public SimulationPanel getSimulationPanel() { return this.mainPanel.getMiddlePanel().getSimulationPanel(); }
    
    public SimulationManager getSimulationManager() { return this.manager; }
}
