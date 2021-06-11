package impl.panels.simulationPanels;

import impl.tools.Tools;
import impl.windows.SimulationWindow;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

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
        
        this.setSize(parent.getInitialWindowSize());
        this.setPreferredSize(parent.getInitialWindowSize());
        
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
        
        // Custom divider look
        //BasicSplitPaneUI divider = new BasicSplitPaneUI() {
        //    @Override
        //    public BasicSplitPaneDivider createDefaultDivider() {
        //        return new BasicSplitPaneDivider(this) {
        //            public void setBorder(Border b) {}
        //            @Override
        //            public void paint(Graphics g) {
        //                g.setColor(Tools.GRAY2);
        //                g.fillRect(0, 0, getSize().width, getSize().height);
        //                g.setColor(Color.gray);
        //                g.drawLine(0, 1, getSize().width, 1);
        //                super.paint(g);
        //            }
        //        };
        //    }
        //};
        //this.setBorder(null);
        //this.setUI(divider);

        //this.setDividerSize(5);
    }
    
    public TopPanel getTopPanel() { return this.topPanel; }
    
    public BottomPanel getBottomPanel() { return this.bottomPanel; }
    
    public SimulationWindow getSimulationWindow() { return this.parent; }
    
    public void onNewGraphImport() {
        topPanel.onNewGraphImport();
        bottomPanel.onNewGraphImport();
    }
}
