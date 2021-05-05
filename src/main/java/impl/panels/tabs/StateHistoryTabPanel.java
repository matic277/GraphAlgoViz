package impl.panels.tabs;

import impl.MyGraph;
import impl.panels.BottomPanel;

import javax.swing.*;
import java.awt.*;

public class StateHistoryTabPanel extends JTabbedPane {
    
    BottomPanel parent;
    MyGraph graph;
    
    int width;
    
    public StateHistoryTabPanel(BottomPanel parent, MyGraph g, int width) {
        this.parent = parent;
        this.graph = g;
        this.width = width;
        
//        this.setSize(panelSize);
//        this.setPreferredSize(new Dimension(width, parent.getHeight()));
        this.setBackground(Color.red);
        
        JPanel hp = new JPanel(); hp.setBackground(Color.magenta);
        JPanel p1 = new JPanel(); hp.setBackground(Color.pink);
        this.addTab("State history", hp);
        this.addTab("Some other tab", p1);
    }
    
}
