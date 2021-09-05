package impl.panels.tabs;

import impl.MyGraph;
import impl.panels.simulationPanels.BottomPanel;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class TabsPanel extends JTabbedPane {
    
    BottomPanel parent;
    MyGraph graph;
    
    StateHistoryTab historyTab;
    ReLayoutTab layoutTab;
    VisualizationTab visualizationTab;
    
    public TabsPanel(BottomPanel parent) {
        this.parent = parent;
        this.graph = MyGraph.getInstance();
        
        //this.setBackground(Tools.GRAY3);
        this.setFont(Tools.getFont(14));
        
        historyTab = new StateHistoryTab(this);
        this.addTab("State history", historyTab);
        //this.setBackgroundAt(0, Tools.GRAY3);
    
        layoutTab = new ReLayoutTab(this);
        this.addTab("Graph layout", layoutTab);
        //this.setBackgroundAt(1, Tools.GRAY3);
    
        visualizationTab = new VisualizationTab(this);
        this.addTab(visualizationTab.getNameOfTab(), visualizationTab);
        //this.setBackgroundAt(2, Tools.GRAY3);
    
        this.setBorder(Tools.UI_BORDER_STANDARD);
        
        // Set min width, so that LeftPanel can't get stretched out too much.
        // Needed because JSplitPane does not respect maxSize, but one minSize
        // of it's children.
        this.addComponentListener(new ComponentListener() {
            @Override public void componentResized(ComponentEvent e) { setMinimumSize(new Dimension(parent.getWidth() - Tools.MAXIMUM_STATS_PANEL_WIDTH-12, 0)); }
            @Override public void componentMoved(ComponentEvent e) { }
            @Override public void componentShown(ComponentEvent e) { }
            @Override public void componentHidden(ComponentEvent e) { }
        });
    }
    
    public StateHistoryTab getStateHistoryTab() { return this.historyTab; }
    
    public void onNewGraphImport() {
        historyTab.onNewGraphImport();
        visualizationTab.onNewGraphImport();
    }
}
