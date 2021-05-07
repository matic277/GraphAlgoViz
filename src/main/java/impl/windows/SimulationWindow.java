package impl.windows;

import core.Window;
import impl.AlgorithmController;
import impl.MyGraph;
import impl.SimulationManager;
import impl.panels.*;
import impl.tools.Tools;

import java.awt.*;

public class SimulationWindow extends Window {
    
    SimulationManager manager;
    MainPanel mainPanel;
    
    final int menuWidth = 150;
    
    public SimulationWindow(MyGraph g, SimulationManager parent) {
        super(new Dimension(Tools.INITIAL_WINDOW_WIDTH, Tools.INITIAL_WINDOW_HEIGHT));
        this.frame.remove(this.panel);
        this.frame.setLayout(new BorderLayout());
        this.manager = parent;
        
        mainPanel = new MainPanel(this);
        this.frame.add(mainPanel, BorderLayout.CENTER);
        
        this.frame.pack();
    }
    
    public SimulationPanel getSimulationPanel() { return this.mainPanel.getTopPanel().getSimulationPanel(); }
    
    public SimulationManager getSimulationManager() { return this.manager; }
    
    public MainPanel getMainPanel() { return this.mainPanel; }
}
