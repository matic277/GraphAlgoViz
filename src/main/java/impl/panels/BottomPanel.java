package impl.panels;

import impl.MyGraph;
import impl.panels.tabs.StateHistoryTabPanel;
import impl.tools.Tools;
import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JSplitPane {
    
    MainPanel parent;
    MyGraph graph;
    
    StateHistoryTabPanel tabPanel;
    StatsPanel statsPanel;
    
    public BottomPanel(MainPanel parent, MyGraph graph) {
        super(JSplitPane.HORIZONTAL_SPLIT, null, null);
        this.parent = parent;
        this.graph = graph;
        
//        this.setLayout(new BorderLayout());
//        Dimension bottomPanelSize = new Dimension(parent.getWidth(), Tools.INITIAL_BOTTOM_MENU_HEIGHT);
//        this.setSize(bottomPanelSize);
//        this.setPreferredSize(bottomPanelSize);
        
//        int statsPanelWidth = 150; // same as MenuPanel
//        int sateHistoryPanelWidth = parent.getSimulationWindow().getWindowSize().width - statsPanelWidth;
        
        statsPanel = new StatsPanel(this);
        tabPanel = new StateHistoryTabPanel(this, graph);
        
//        this.add(statsPanel, BorderLayout.WEST);
//        this.add(tabPanel, BorderLayout.CENTER);
        
        this.setLeftComponent(statsPanel);
        this.setRightComponent(tabPanel);
        
        
    }
}
