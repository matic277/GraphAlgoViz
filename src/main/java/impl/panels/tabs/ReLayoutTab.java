package impl.panels.tabs;

import core.LayoutType;
import impl.MyGraph;
import impl.graphBuilders.GraphBuilder;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class ReLayoutTab extends JPanel {
    
    TabsPanel parent;
    MyGraph graph;
    
    JComboBox<LayoutType> layoutTypeDropdown;
    JButton doLayoutBtn;
    
    LayoutType selectedLayout = LayoutType.values()[0];
    
    public ReLayoutTab(TabsPanel parent) {
        this.parent = parent;
        this.graph = MyGraph.getInstance();
        
        //this.setBackground(Tools.GRAY3);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        initInterface();
    }
    
    private static final AtomicInteger THREADS_DOING_LAYOUT = new AtomicInteger(0);
    
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
                // Only 1 thread can be doing layout at once!
                synchronized (THREADS_DOING_LAYOUT) {
                        if (THREADS_DOING_LAYOUT.get() > 0) return;
                    System.out.println("DOING!");
                        THREADS_DOING_LAYOUT.incrementAndGet();
                }
                
                doLayoutBtn.setEnabled(false);
                
                CompletableFuture.runAsync(() -> {
                    // run layout algorithm
                    // signal status with processing label
                    processingLbl.setText(" Doing layout...");
                    processingLbl.setBorder(new Tools.RoundBorder(Tools.GREEN, new BasicStroke(2), 10));
                    processingLbl.setPreferredSize(new Dimension(155, 26));
                    processingLbl.setVisible(true);
                    
                    GraphBuilder.layoutTypeMap.get(selectedLayout).run();
                    
                    processingLbl.setPreferredSize(new Dimension(75, 26));
                    processingLbl.setText(" Done!");
                    doLayoutBtn.setEnabled(true);
                }).thenApply((x) -> {
                    Tools.sleep(2000);
                    processingLbl.setVisible(false);
                    
                    synchronized (THREADS_DOING_LAYOUT) { THREADS_DOING_LAYOUT.getAndDecrement(); return null; }
                }).exceptionally(e -> {
                    doLayoutBtn.setEnabled(true);
                    // on error signal message
                    processingLbl.setVisible(false);
                    
                    errorlbl.setText(" " + e.getCause().getLocalizedMessage() + " ");
                    errorlbl.setBorder(new Tools.RoundBorder(Tools.RED, new BasicStroke(2), 10));
                    errorlbl.setPreferredSize(new Dimension((int) errorlbl.getPreferredSize().getWidth(), 26));
                    
                    this.revalidate(); // needed otherwise size of errorlbl bugs out
                    this.doLayout();
                    this.repaint();
                    
                    errorlbl.setVisible(true);
                    Tools.sleep(4000);
                    errorlbl.setVisible(false);
                    
                    synchronized (THREADS_DOING_LAYOUT) { THREADS_DOING_LAYOUT.getAndDecrement(); return null; }
                });
        });
        
        this.add(info);
        this.add(layoutTypeDropdown);
        this.add(doLayoutBtn);
        this.add(errorlbl);
        this.add(processingLbl);
        
        this.repaint();
    }
}
