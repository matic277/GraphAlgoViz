package impl.panels.tabs;

import impl.MyGraph;
import impl.panels.BottomPanel;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class TabsPanel extends JTabbedPane {
    
    BottomPanel parent;
    MyGraph graph;
    
    StateHistoryTab historyTab;
    
    public TabsPanel(BottomPanel parent) {
        this.parent = parent;
        this.graph = MyGraph.getInstance();
        
        this.setBackground(Tools.GRAY3);
        this.setFont(Tools.getFont(14));
        
        historyTab = new StateHistoryTab(this);
        this.addTab("State history", historyTab);
        this.setBackgroundAt(0, Tools.GRAY3);
        
        JPanel p1 = new JPanel(); p1.setBackground(Color.pink);
        this.addTab("Some other tab", p1);
        this.setBackgroundAt(1, Tools.GRAY3);
        
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
    }
}
