package impl.panels.tabs;

import core.LayoutType;
import impl.MyGraph;
import impl.graphBuilders.CliqueGraphBuilder;
import impl.graphBuilders.GraphBuilder;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.RunnableFuture;

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
        
        JLabel errorLabel = new JLabel();
        // creating borders makes label visible even when
        // visibility is set to FALSE; -> swing bug??
//        errorLabel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Tools.RED));
        errorLabel.setFont(Tools.getBoldFont(14));
        errorLabel.setForeground(Tools.RED);
        errorLabel.setVisible(true); // off by default
        
        doLayoutBtn = new JButton("Apply");
        doLayoutBtn.setFont(Tools.getFont(14));
        doLayoutBtn.addActionListener(a -> {
            try {
                GraphBuilder.layoutTypeMap.get(selectedLayout).run();
            } catch (Exception e) {
                // catch JGraphT exceptions, some graphs can't be laid out
                // in selected way. Like bipartite layout.
                errorLabel.setText(" " + e.getLocalizedMessage() + " ");
                errorLabel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Tools.RED));
                errorLabel.setVisible(true);
                // Turn error reporting off after some seconds
                CompletableFuture.runAsync(() -> {
                    Tools.sleep(5000);
                    errorLabel.setVisible(false);
                });
            }
        });
        
        this.add(info);
        this.add(layoutTypeDropdown);
        this.add(doLayoutBtn);
        this.add(errorLabel);
        
        this.repaint();
    }
}
