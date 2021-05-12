package impl.panels;

import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;

public class Content extends JComponent {
    
    public JLabel titleLbl;
    public JLabel nodesNumTextLbl;
    public JLabel edgesNumTextLbl;
    public JLabel percentInformedNumTextLbl;
    public JLabel totalInformedNumTextLbl;
    
    public JLabel nodesNumLbl;
    public JLabel edgesNumLbl;
    public JLabel percentInformedNumLbl;
    public JLabel totalInformedNumLbl;
    
    public Content() { initContent(); }
    
    public void initContent() {
        this.setLayout(new BorderLayout());
        
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(1, 1, 0, 0));
        titleLbl = new JLabel("  Graph statistics ");
        titleLbl.setOpaque(true);
        titleLbl.setFont(Tools.getFont(14));
        titleLbl.setBackground(Tools.GRAY);
        titleLbl.setSize(new Dimension(150, 30));
        titleLbl.setPreferredSize(new Dimension(150, 30));
        titlePanel.add(titleLbl);
        
        
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(true);
        textPanel.setBackground(Color.white);
        textPanel.add(dummySeparator());
        
        nodesNumTextLbl = new JLabel("  Number of nodes:");
        nodesNumTextLbl.setOpaque(true);
        nodesNumTextLbl.setBackground(Color.white);
        nodesNumTextLbl.setFont(Tools.getFont(12));
        textPanel.add(nodesNumTextLbl);
        textPanel.add(dummySeparator());
    
        edgesNumTextLbl = new JLabel("  Number of edges:");
        edgesNumTextLbl.setOpaque(true);
        edgesNumTextLbl.setBackground(Color.white);
        edgesNumTextLbl.setFont(Tools.getFont(12));
        textPanel.add(edgesNumTextLbl);
        textPanel.add(dummySeparator());
    
        percentInformedNumTextLbl = new JLabel("  Percent of informed nodes:");
        percentInformedNumTextLbl.setOpaque(true);
        percentInformedNumTextLbl.setBackground(Color.white);
        percentInformedNumTextLbl.setFont(Tools.getFont(12));
        textPanel.add(percentInformedNumTextLbl);
        textPanel.add(dummySeparator());
        
        totalInformedNumTextLbl = new JLabel("  Total informed nodes:");
        totalInformedNumTextLbl.setOpaque(true);
        totalInformedNumTextLbl.setBackground(Color.white);
        totalInformedNumTextLbl.setFont(Tools.getFont(12));
        textPanel.add(totalInformedNumTextLbl);
        
        
        
        JPanel valPanel = new JPanel();
        valPanel.setLayout(new BoxLayout(valPanel, BoxLayout.Y_AXIS));
        valPanel.setOpaque(true);
        valPanel.setBackground(Color.white);
        valPanel.add(dummySeparator());
        
        nodesNumLbl = new JLabel("  0");
        nodesNumLbl.setOpaque(true);
        nodesNumLbl.setBackground(Color.white);
        nodesNumLbl.setFont(Tools.getFont(12));
        valPanel.add(nodesNumLbl);
        valPanel.add(dummySeparator());
        
        edgesNumLbl = new JLabel("  0");
        edgesNumLbl.setOpaque(true);
        edgesNumLbl.setBackground(Color.white);
        edgesNumLbl.setFont(Tools.getFont(12));
        valPanel.add(edgesNumLbl);
        valPanel.add(dummySeparator());
        
        percentInformedNumLbl = new JLabel("  0");
        percentInformedNumLbl.setOpaque(true);
        percentInformedNumLbl.setBackground(Color.white);
        percentInformedNumLbl.setFont(Tools.getFont(12));
        valPanel.add(percentInformedNumLbl);
        valPanel.add(dummySeparator());
        
        totalInformedNumLbl = new JLabel("  0");
        totalInformedNumLbl.setOpaque(true);
        totalInformedNumLbl.setBackground(Color.white);
        totalInformedNumLbl.setFont(Tools.getFont(12));
        valPanel.add(totalInformedNumLbl);
        
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(textPanel, BorderLayout.WEST);
        this.add(valPanel, BorderLayout.CENTER);
    }
    
    private JLabel dummySeparator() {
        Dimension size = new Dimension(this.getWidth(), 5);
        JLabel lbl = new JLabel();
        lbl.setSize(size);
        lbl.setPreferredSize(size);
        lbl.setMinimumSize(size);
        lbl.setMaximumSize(size);
//        lbl.setBackground(Color.WHITE);
//        lbl.setOpaque(true);
        return lbl;
    }
}
