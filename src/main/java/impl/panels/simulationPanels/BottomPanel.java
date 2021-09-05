package impl.panels.simulationPanels;

import impl.MyGraph;
import impl.panels.tabs.TabsPanel;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
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
        //this.setBackground(Tools.GRAY3);
        
        // set location of divider, so that width
        // of statsPanel is set to initial state
        this.setDividerLocation(Tools.MAXIMUM_STATS_PANEL_WIDTH);

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
//                        super.paint(g);
//                    }
//                };
//            }
//        };
//        this.setBorder(null);
//        this.setUI(divider);
        
        this.setDividerSize(5);
    }
    
    public StatsPanel getStatsPanel() { return this.statsPanel; }
    public TabsPanel getTabsPanel() { return this.tabPanel; }
    
    public void onNewGraphImport() {
        tabPanel.onNewGraphImport();
        statsPanel.onNewGraphImport();
    }
}
