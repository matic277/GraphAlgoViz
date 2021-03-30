package impl.windows;

import core.Window;
import impl.panels.MenuPanel;
import impl.panels.SimulationPanel;

import java.awt.*;

public class SimulationWindow extends Window {
    
    SimulationPanel simPanel;
    MenuPanel menuPanel;
    final int menuWidth = 150;
    
    public SimulationWindow(Dimension windowSize) {
        super(windowSize);
        this.frame.remove(this.panel);
        this.frame.setLayout(new BorderLayout());
        
        Dimension menuSize = new Dimension(menuWidth, windowSize.height);
        Dimension simSize = new Dimension(windowSize.width-menuWidth, windowSize.height);
        
        this.simPanel = new SimulationPanel(this, simSize);
        this.menuPanel = new MenuPanel(this, menuSize);
        
        this.frame.add(menuPanel, BorderLayout.WEST);
        this.frame.add(simPanel, BorderLayout.CENTER);
        
        this.frame.pack();
    }
}
