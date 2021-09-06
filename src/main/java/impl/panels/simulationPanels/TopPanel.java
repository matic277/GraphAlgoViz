package impl.panels.simulationPanels;

import impl.tools.Tools;
import impl.windows.SimulationWindow;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

public class TopPanel extends JPanel {
    
    MainPanel parent;
    
    SimulationPanel simPanel;
    MenuPanel menuPanel;
    
    public TopPanel(MainPanel parent) {
        this.parent = parent;
        
        simPanel = new SimulationPanel(this);
        menuPanel = new MenuPanel(this);
        
        this.setLayout(new BorderLayout());
        
        this.add(menuPanel, BorderLayout.NORTH);
        this.add(simPanel, BorderLayout.CENTER);
        
        // set location of divider, so that width
        // of left menu is set to initial state
        //this.setDividerLocation(Tools.INITIAL_LEFT_MENU_WIDTH);
        //this.setResizeWeight(0);

        // Custom divider look
//        BasicSplitPaneUI divider = new BasicSplitPaneUI() {
//            @Override
//            public BasicSplitPaneDivider createDefaultDivider() {
//                return new BasicSplitPaneDivider(this) {
//                    public void setBorder(Border b) {}
//                    @Override
//                    public void paint(Graphics g) {
//                        g.setColor(Tools.GRAY2);
//                        g.fillRect(0, 0, getSize().width, getSize().height);
//                        g.setColor(Color.GRAY);
//                        g.drawLine(getSize().width-2, 0, getSize().width-2, getSize().height);
//                        super.paint(g);
//                    }
//                };
//            }
//        };
//        this.setBorder(null);
//        this.setUI(divider);

        //this.setDividerSize(5);
    }
    
    public SimulationWindow getSimulationWindow() { return this.parent.getSimulationWindow(); }
    
    public SimulationPanel getSimulationPanel() { return this.simPanel; }
    
    public MainPanel getMainPanel() { return this.parent; }
}
