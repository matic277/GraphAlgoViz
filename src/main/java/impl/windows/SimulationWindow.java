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
    
//        System.out.println("menu: " + menuSize);
//        System.out.println("sims: " + simSize);
        
        this.simPanel = new SimulationPanel(this, simSize);
        this.menuPanel = new MenuPanel(this, menuSize);
        
        this.frame.add(menuPanel, BorderLayout.WEST);
        this.frame.add(simPanel, BorderLayout.CENTER);
//        simPanel.updateUI();
//        menuPanel.updateUI();
        
//        Listener l = new Listener();
//        this.panel.addMouseMotionListener(l);
//        this.panel.addMouseListener(l);
        
        this.frame.pack();
    }
}
