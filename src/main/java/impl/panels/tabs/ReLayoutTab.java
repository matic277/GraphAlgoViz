package impl.panels.tabs;

import core.LayoutType;
import impl.MyGraph;
import impl.graphBuilders.GraphBuilder;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class ReLayoutTab extends JPanel {
    
    TabsPanel parent;
    MyGraph graph;
    
    JComboBox<LayoutType> layoutTypeDropdown;
    JButton doLayoutBtn;
    
    LayoutType selectedLayout = LayoutType.values()[0];
    
    
    public ReLayoutTab(TabsPanel parent) {
        this.parent = parent;
        this.graph = MyGraph.getInstance();
        
        this.setBackground(Tools.GRAY3);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        initInterface();
    }
    
    private void initInterface() {
        JLabel info = new JLabel(" Select type of layout:");
        info.setFont(Tools.getFont(14));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        layoutTypeDropdown = new JComboBox<>(LayoutType.values());
        ((JLabel)layoutTypeDropdown.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        layoutTypeDropdown.setFont(Tools.getFont(14));
        layoutTypeDropdown.setVisible(true);
        layoutTypeDropdown.setEnabled(true);
        layoutTypeDropdown.addActionListener(a -> selectedLayout = (LayoutType)layoutTypeDropdown.getSelectedItem());
        
        JLabel errorlbl = new JLabel();
        // creating borders makes label visible even when
        // visibility is set to FALSE; -> swing bug??
//        errorLabel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Tools.RED));
        errorlbl.setFont(Tools.getMonospacedFont(14));
        errorlbl.setForeground(Tools.RED);
        errorlbl.setVisible(true); // off by default
    
        JLabel processingLbl = new JLabel();
        processingLbl.setFont(Tools.getMonospacedFont(14));
        processingLbl.setForeground(Tools.GREEN);
        processingLbl.setVisible(true); // off by default
        
        doLayoutBtn = new JButton("Apply");
        doLayoutBtn.setFont(Tools.getFont(14));
        doLayoutBtn.addActionListener(a -> {
            try {
                doLayoutBtn.setEnabled(false);
                
                CompletableFuture.runAsync(() -> {
                    // run layout algorithm
                    // signal with processing label
                    System.out.println("here");
                    processingLbl.setText(" Doing layout... ");
                    processingLbl.setBorder(new Tools.RoundBorder(Tools.GREEN, new BasicStroke(2), 10));
                    processingLbl.setPreferredSize(new Dimension((int) processingLbl.getPreferredSize().getWidth(), 30));
                    processingLbl.setVisible(true);
                    
                    GraphBuilder.layoutTypeMap.get(selectedLayout).run();
                    
                    processingLbl.setText(" Done! ");
                    Tools.sleep(1000);
                    processingLbl.setVisible(false);
                    System.out.println("done");
                    
                }).exceptionally(e -> {
                    System.out.println("error");
                    doLayoutBtn.setEnabled(true);
                    // on error signal message
                    processingLbl.setVisible(false);
                    
                    errorlbl.setText(" " + e.getLocalizedMessage() + " ");
                    errorlbl.setBorder(new Tools.RoundBorder(Tools.RED, new BasicStroke(2), 10));
                    errorlbl.setPreferredSize(new Dimension((int) errorlbl.getPreferredSize().getWidth(), 30));
                    errorlbl.setVisible(true);
                    
                    Tools.sleep(5000);
                    errorlbl.setVisible(false);
                    return null;
                }).thenApply((x) -> {
                    doLayoutBtn.setEnabled(true);
                    return null;
                });
               
            } catch (Exception e) {
                // catch JGraphT exceptions, some graphs can't be laid out
                // in selected way. Like bipartite layout.
                
            }
        });
        
        this.add(info);
        this.add(layoutTypeDropdown);
        this.add(doLayoutBtn);
        this.add(errorlbl);
        this.add(processingLbl);
        
        this.repaint();
    }
}
