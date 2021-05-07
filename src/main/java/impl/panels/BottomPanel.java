package impl.panels;

import impl.MyGraph;
import impl.panels.tabs.TabsPanel;
import impl.tools.Tools;

import javax.swing.*;

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
    
        // set location of divider, so that width
        // of statsPanel is set to initial state
        this.setDividerLocation(Tools.INITIAL_LEFT_MENU_WIDTH);
    }
    
    public StatsPanel getStatsPanel() { return this.statsPanel; }
    public TabsPanel getTabsPanel() { return this.tabPanel; }
}
