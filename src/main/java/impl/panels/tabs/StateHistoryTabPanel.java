package impl.panels.tabs;

import impl.MyGraph;
import impl.panels.BottomPanel;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;

public class StateHistoryTabPanel extends JTabbedPane {
    
    BottomPanel parent;
    MyGraph graph;
    
    public StateHistoryTabPanel(BottomPanel parent, MyGraph g) {
        this.parent = parent;
        this.graph = g;
        
//        Dimension panelSize = new Dimension(parent.getWidth() - Tools.INITIAL_STATS_PANEL_WIDTH, parent.getHeight());
//        this.setSize(panelSize);
//        this.setPreferredSize(panelSize);
        
        this.setBackground(Color.red);
        
        JPanel hp = new JPanel(); hp.setBackground(Color.magenta);
        JPanel p1 = new JPanel(); hp.setBackground(Color.pink);
        this.addTab("State history", hp);
        this.addTab("Some other tab", p1);
    }
    
}
