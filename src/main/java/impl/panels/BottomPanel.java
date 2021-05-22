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
    
    public BottomPanel(MainPanel parent) {
        super(JSplitPane.HORIZONTAL_SPLIT, null, null);
        this.parent = parent;
        this.graph = MyGraph.getInstance();
        
        statsPanel = new StatsPanel(this);
        tabPanel = new TabsPanel(this);
    
        statsPanel.setMaximumSize(new Dimension(325, Integer.MAX_VALUE));
        
        this.setLeftComponent(statsPanel);
        this.setRightComponent(tabPanel);
        this.setBackground(Tools.GRAY3);
        
        // set location of divider, so that width
        // of statsPanel is set to initial state
        this.setDividerLocation(Tools.MAXIMUM_STATS_PANEL_WIDTH);
    }
    
    public StatsPanel getStatsPanel() { return this.statsPanel; }
    public TabsPanel getTabsPanel() { return this.tabPanel; }
    
    public void onNewGraphImport() {
        tabPanel.onNewGraphImport();
        statsPanel.onNewGraphImport();
    }
}
