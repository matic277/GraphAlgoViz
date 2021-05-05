package impl.panels;

import impl.tools.Tools;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

public class StatsPanel extends JPanel {
    
    int width;
    
    JLabel titleLbl;
    
    JLabel nodesNumLbl;
    JLabel edgesNumLbl;
    JLabel informedNumLbl;
    
    int informedNum, uninformedNum;
    double informedPercent;
    
    public StatsPanel(BottomPanel parent, int width) {
        this.width = width;
        this.setBackground(Color.blue);
        
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(width, parent.getHeight()));
        this.setPreferredSize(new Dimension(width, parent.getHeight()));
        
        
        titleLbl = new JLabel(" Graph statistics ");
        titleLbl.setOpaque(true);
        titleLbl.setFont(Tools.getFont(14));
        titleLbl.setBackground(Color.YELLOW);
        this.add(titleLbl, BorderLayout.NORTH);
    
        initStatistics();
        
        // so that this panel can be squished, hiding its components
        // otherwise components dictate smallest possible size
        this.setMinimumSize(new Dimension(0, 0));
    }
    
    private void initStatistics() {
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(true);
        contentPanel.setBackground(Color.blue);
        contentPanel.setLayout(new GridLayout(4, 1, 5, 5));
        this.add(contentPanel, BorderLayout.CENTER);
        
        nodesNumLbl = new JLabel(" Number of nodes: 0");
        nodesNumLbl.setOpaque(true);
        nodesNumLbl.setFont(Tools.getFont(12));
        nodesNumLbl.setBackground(Color.white);
        contentPanel.add(nodesNumLbl, BorderLayout.CENTER);
    
        edgesNumLbl = new JLabel(" Number of edges: 0");
        edgesNumLbl.setOpaque(true);
        edgesNumLbl.setFont(Tools.getFont(12));
        edgesNumLbl.setBackground(Color.white);
        contentPanel.add(edgesNumLbl, BorderLayout.CENTER);
        
        
        informedNumLbl = new JLabel(" Informed | Uninformed nodes: 0 | 0 (0%)");
        informedNumLbl.setOpaque(true);
        informedNumLbl.setFont(Tools.getFont(12));
        informedNumLbl.setBackground(Color.white);
        contentPanel.add(informedNumLbl, BorderLayout.CENTER);
    }
}
