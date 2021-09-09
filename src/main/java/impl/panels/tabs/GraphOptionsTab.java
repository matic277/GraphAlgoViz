package impl.panels.tabs;

import core.LayoutType;
import impl.MyGraph;
import impl.Pair;
import impl.layouts.VerticalFlowLayout;
import impl.nodeinformator.NodeInformator;
import impl.nodeinformator.NodeInformatorProperties;
import impl.tools.InputHelpers;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class GraphOptionsTab extends JPanel {
    
    TabsPanel parent;
    MyGraph graph;
    
    JComboBox<LayoutType> layoutTypeDropdown;
    JButton doLayoutBtn;
    
    JTextField inputfield;
    JButton reinformBtn;
    
    LayoutType selectedLayout = LayoutType.values()[0];
    
    public GraphOptionsTab(TabsPanel parent) {
        this.parent = parent;
        this.graph = MyGraph.getInstance();
        
        //this.setBackground(Tools.GRAY3);
        //this.setLayout(new FlowLayout(FlowLayout.LEFT));
    
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP));
        
        initGraphLayoutOption();
        initGraphInformatorOption();
    }
    
    private static final AtomicInteger THREADS_DOING_LAYOUT = new AtomicInteger(0);
    
    private void initGraphLayoutOption() {
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel info = new JLabel("  Select type of layout:            "); // TODO fix alignment
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        layoutTypeDropdown = new JComboBox<>(LayoutType.values());
        ((JLabel)layoutTypeDropdown.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        layoutTypeDropdown.setVisible(true);
        layoutTypeDropdown.setEnabled(true);
        layoutTypeDropdown.addActionListener(a -> selectedLayout = (LayoutType)layoutTypeDropdown.getSelectedItem());
        
        JLabel errorlbl = new JLabel();
        // creating borders makes label visible even when
        // visibility is set to FALSE; -> swing bug??
//        errorLabel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Tools.RED));
//        errorlbl.setFont(Tools.getMonospacedFont(14));
        errorlbl.setForeground(Tools.RED);
        errorlbl.setVisible(true); // off by default

        JLabel processingLbl = new JLabel();
        //processingLbl.setFont(Tools.getMonospacedFont(14));
        processingLbl.setForeground(Tools.GREEN);
        processingLbl.setVisible(true); // off by default
        
        doLayoutBtn = new JButton("Apply");
        
        // TODO there has got to be a more straight forward
        // way to do this. Also, make a class out of this
        // so it can be used by and button click that
        // gives feedback
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
                    processingLbl.setPreferredSize(new Dimension(100, 24));
                    processingLbl.setVisible(true);
                    
                    selectedLayout.getLayoutExecutor().run();
                    
                    processingLbl.setPreferredSize(new Dimension(58, 24));
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
        
        container.add(info);
        container.add(layoutTypeDropdown);
        container.add(doLayoutBtn);
        container.add(errorlbl);
        container.add(processingLbl);
    
        //container.setPreferredSize(new Dimension(800, 100));
        //container.setMaximumSize(new Dimension(800, 100));
        //container.setAlignmentX(RIGHT_ALIGNMENT);
        //container.setBackground(Color.black);
        
        this.add(container);
    
        this.doLayout();
        container.doLayout();
    }
    
    private void initGraphInformatorOption() {
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel info = new JLabel("  Randomly re-inform nodes:");
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        
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
    
        inputfield = new JTextField();
        
        reinformBtn = new JButton("Apply");
        reinformBtn.addActionListener(a -> onReinformButton());
        
        container.add(info);
        container.add(inputfield);
        container.add(reinformBtn);
        container.add(errorlbl);
        container.add(processingLbl);
    
        //container.setPreferredSize(new Dimension(800, 100));
        //container.setMaximumSize(new Dimension(800, 100));
        //container.setAlignmentX(RIGHT_ALIGNMENT);
        //container.setBackground(Color.yellow);
        
        this.add(container);
    
        // this must be called in order for
        // JComponent.getBounds() to be calculated properly
        this.doLayout();
        container.doLayout();
        
        // Resize to match width fo upper component
        inputfield.setPreferredSize(new Dimension(layoutTypeDropdown.getWidth(), inputfield.getHeight()));
    }
    
    private void onReinformButton() {
        Pair<Number, Boolean> informedInfo = null;
        try {
            informedInfo = InputHelpers.performInputValidationDoubleOrInteger(inputfield.getText());
        } catch (RuntimeException re) {
            System.out.println("Input examples: \"10\" or \"10%\".");
            return;
        }
        
        boolean isPercentage = informedInfo.getB();
        Number informedNodes = informedInfo.getA();
    
        NodeInformatorProperties informatorProperties = new NodeInformatorProperties();
        informatorProperties.setTotalNodesToInform(isPercentage ? null : informedNodes.intValue());
        informatorProperties.setInformedProbability(isPercentage ? informedNodes.doubleValue() : null);
        
        // before running informator, make all nodes uninformed!
        MyGraph.getInstance().getGraph().vertexSet().forEach(v -> v.states.get(0).setState(0));
        
        new NodeInformator(informatorProperties).run();
    }
}
