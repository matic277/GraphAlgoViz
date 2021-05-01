package impl.windows;

import core.Window;
import impl.AlgorithmController;
import impl.MyGraph;
import impl.SimulationManager;
import impl.panels.MenuPanel;
import impl.panels.SimulationPanel;

import java.awt.*;

public class SimulationWindow extends Window {
    
    SimulationManager manager;
    
    SimulationPanel simPanel;
    MenuPanel menuPanel;
    final int menuWidth = 150;
    
    public SimulationWindow(Dimension windowSize, MyGraph g, SimulationManager parent) {
        super(windowSize);
        this.frame.remove(this.panel);
        this.frame.setLayout(new BorderLayout());
        this.manager = parent;
        
        Dimension menuSize = new Dimension(menuWidth, windowSize.height);
        Dimension simSize = new Dimension(windowSize.width-menuWidth, windowSize.height);
        
        this.simPanel = new SimulationPanel(this, g, simSize);
        this.menuPanel = new MenuPanel(this, simPanel, menuSize);
        
        this.frame.add(menuPanel, BorderLayout.WEST);
        this.frame.add(simPanel, BorderLayout.CENTER);
        
        this.frame.pack();
    }
    
    public SimulationPanel getSimulationPanel() {
        return this.simPanel;
    }
    
    public SimulationManager getSimulationManager() {
        return this.manager;
    }
}
