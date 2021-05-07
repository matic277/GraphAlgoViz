package impl.panels;

import impl.MyGraph;
import impl.panels.tabs.TabsPanel;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JSplitPane {
    
    MainPanel parent;
    MyGraph graph;
    
    TabsPanel tabPanel;
    StatsPanel statsPanel;
    
    public BottomPanel(MainPanel parent, MyGraph graph) {
        super(JSplitPane.HORIZONTAL_SPLIT, null, null);
        this.parent = parent;
        this.graph = graph;
        
        statsPanel = new StatsPanel(this);
        tabPanel = new TabsPanel(this, graph);
        
        this.setLeftComponent(statsPanel);
        this.setRightComponent(tabPanel);
    }
    
    public StatsPanel getStatsPanel() { return this.statsPanel; }
    public TabsPanel getTabsPanel() { return this.tabPanel; }
}
