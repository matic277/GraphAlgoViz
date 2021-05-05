package impl.panels;

import impl.MyGraph;
import impl.panels.tabs.StateHistoryTabPanel;
import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JSplitPane {
    
    SimulationWindow parent;
    MyGraph graph;
    
    StateHistoryTabPanel tabPanel;
    StatsPanel statsPanel;
    
    int height;
    
    public BottomPanel(SimulationWindow parent, MyGraph graph, int height) {
        super(JSplitPane.HORIZONTAL_SPLIT, null, null);
        this.parent = parent;
        this.graph = graph;
        this.height = height;
        
//        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(parent.getWindowSize().width, height));
        this.setPreferredSize(new Dimension(parent.getWindowSize().width, height));
        
        int statsPanelWidth = 150; // same as MenuPanel
        int sateHistoryPanelWidth = parent.getWindowSize().width - statsPanelWidth;
        
        statsPanel = new StatsPanel(this, statsPanelWidth);
        tabPanel = new StateHistoryTabPanel(this, graph, sateHistoryPanelWidth);
        
//        this.add(statsPanel, BorderLayout.WEST);
//        this.add(tabPanel, BorderLayout.CENTER);
        
        this.setLeftComponent(statsPanel);
        this.setRightComponent(tabPanel);
        
        
    }
}
