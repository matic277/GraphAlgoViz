package impl.panels;

import impl.MyGraph;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

public class StatsPanel extends JPanel {
    
    JLabel titleLbl;
    
    JLabel nodesNumLbl;
    JLabel edgesNumLbl;
    JLabel informedNumLbl;
    
    int informedNum, uninformedNum;
    double informedPercent;
    
    public StatsPanel(BottomPanel parent) {
        Dimension panelSize = new Dimension(Tools.INITIAL_STATS_PANEL_WIDTH, parent.getHeight());
        this.setSize(panelSize);
        this.setPreferredSize(panelSize);
    
        this.setLayout(new BorderLayout());
        this.setBackground(Tools.MEUN_COLORS);
        
        titleLbl = new JLabel("  Graph statistics ");
        titleLbl.setOpaque(true);
        titleLbl.setFont(Tools.getFont(14));
        titleLbl.setBackground(Tools.GRAY);
        titleLbl.setPreferredSize(new Dimension(150, 30));
        this.add(titleLbl, BorderLayout.NORTH);
    
        initStatistics();
        
        // so that this panel can be squished, hiding its components
        // otherwise components dictate smallest possible size
        this.setMinimumSize(new Dimension(0, 0));
    }
    
    private void initStatistics() {
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(true);
        contentPanel.setBackground(Tools.MEUN_COLORS);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        this.add(contentPanel, BorderLayout.CENTER);
    
        contentPanel.add(dummySeparator());
        
        nodesNumLbl = new JLabel("  Number of nodes: 0");
//        nodesNumLbl.setOpaque(true);
        nodesNumLbl.setFont(Tools.getFont(12));
//        nodesNumLbl.setBackground(Color.white);
        contentPanel.add(nodesNumLbl);
        
        contentPanel.add(dummySeparator());
    
        edgesNumLbl = new JLabel("  Number of edges: 0");
//        edgesNumLbl.setOpaque(true);
        edgesNumLbl.setFont(Tools.getFont(12));
//        edgesNumLbl.setBackground(Color.white);
        contentPanel.add(edgesNumLbl);
    
        contentPanel.add(dummySeparator());

        informedNumLbl = new JLabel("  Informed | Uninformed nodes: 0 | 0 (0%)");
//        informedNumLbl.setOpaque(true);
        informedNumLbl.setFont(Tools.getFont(12));
//        informedNumLbl.setBackground(Color.white);
        contentPanel.add(informedNumLbl);
    }
    
    private JLabel dummySeparator() {
        Dimension size = new Dimension(this.getWidth(), 5);
        JLabel lbl = new JLabel();
        lbl.setSize(size);
        lbl.setPreferredSize(size);
        lbl.setMinimumSize(size);
        lbl.setMaximumSize(size);
//        lbl.setBackground(Color.white);
//        lbl.setOpaque(true);
        return lbl;
    }
    
    public void onNewGraphImport() {
        // TODO
    }
}
