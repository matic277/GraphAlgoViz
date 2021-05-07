package impl.panels.tabs;

import impl.MyGraph;
import impl.panels.BottomPanel;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;

public class TabsPanel extends JTabbedPane {
    
    BottomPanel parent;
    MyGraph graph;
    
    StateHistoryTab historyTab;
    
    public TabsPanel(BottomPanel parent, MyGraph g) {
        this.parent = parent;
        this.graph = g;
        
        this.setBackground(Color.red);
        
        historyTab = new StateHistoryTab(this);
        this.addTab("State history", historyTab);
        
        JPanel p1 = new JPanel(); p1.setBackground(Color.pink);
        this.addTab("Some other tab", p1);
    }
    
    public StateHistoryTab getStateHistoryTab() { return this.historyTab; }
    
}