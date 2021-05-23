package impl.panels.importPanels;

import core.GraphType;
import core.LayoutType;
import impl.graphBuilders.GraphBuilder;
import impl.panels.importPanels.graphOptionPanels.OptionPanel;
import impl.tools.Tools;
import impl.windows.ImportGraphWindow;

import javax.swing.*;
import java.awt.*;

import static impl.tools.Tools.getBoldFont;

public class ImportGraphPanel extends JPanel {
    
    ImportGraphWindow parent;
    
    // NORTH
    JPanel titlePanel;
        JLabel titleText1;
        JLabel titleText2;
    
    // CENTER
    JPanel middlePanel;
        JPanel dropdownPanel;
            JPanel graphTypePanel;
                JLabel selectGraphText;
                JComboBox<GraphType> graphTypeDropdown;
            JPanel layoutTypePanel;
                JLabel selectLayoutText;
                JComboBox<LayoutType> layoutTypeDropdown;
            
        JPanel optionPanelContainer;
            OptionPanel optionPanel;
    
    // SOUTH
    JButton importBtn;
    
    GraphType selectedGraphType;
    
    
    public ImportGraphPanel(ImportGraphWindow parent) {
        this.parent = parent;
        
        this.setOpaque(true);
        this.setBackground(Tools.bgColor);
        this.setPreferredSize(Tools.INITIAL_IMPORT_WINDOW_SIZE);
        this.setLayout(new BorderLayout());
        
        int borderWidth = 75;
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(borderWidth, 100));
        spacer.setMaximumSize(new Dimension(borderWidth, 100));
        spacer.setMinimumSize(new Dimension(borderWidth, 100));
        JLabel spacer2 = new JLabel();
        spacer2.setPreferredSize(new Dimension(borderWidth, 100));
        spacer2.setMaximumSize(new Dimension(borderWidth, 100));
        spacer2.setMinimumSize(new Dimension(borderWidth, 100));
        this.add(spacer, BorderLayout.WEST);
        this.add(spacer2, BorderLayout.EAST);
        
//        selectedGraphType = GraphType.STATIC_TEST;
//        optionPanel = selectedGraphType.getPanel();
        
        initNorth();
        initCenter();
        
        
        JPanel container = new JPanel();
        container.setBackground(Tools.bgColor);
        importBtn = new JButton("Import");
        importBtn.setFont(Tools.getFont(14));
        importBtn.setPreferredSize(new Dimension(200, 35));
        importBtn.addActionListener(optionPanel.getButtonAction(selectedGraphType, parent.getFrame()));
        container.add(importBtn);
        
        this.add(container, BorderLayout.SOUTH);
    }
    
    private void initCenter() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        this.add(middlePanel, BorderLayout.CENTER);
        
        dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        middlePanel.add(dropdownPanel, BorderLayout.NORTH);
        
        layoutTypePanel = new JPanel();
        layoutTypePanel.setBackground(Tools.bgColor);
        layoutTypePanel.setLayout(new BoxLayout(layoutTypePanel, BoxLayout.Y_AXIS));
        dropdownPanel.add(layoutTypePanel, BorderLayout.NORTH);
        
        graphTypePanel = new JPanel();
        graphTypePanel.setBackground(Tools.bgColor);
        graphTypePanel.setLayout(new BoxLayout(graphTypePanel, BoxLayout.Y_AXIS));
        dropdownPanel.add(graphTypePanel, BorderLayout.CENTER);
        
        optionPanelContainer = new JPanel();
        optionPanelContainer.setLayout(new BorderLayout());
        middlePanel.add(optionPanelContainer, BorderLayout.CENTER);
        
        initGraphTypeInputs();
        initLayoutTypeInputs();
        initOptionPanel();
    }
    
    private void initOptionPanel() {
        selectedGraphType = GraphType.STATIC_TEST;
        selectedGraphType.getPanel().setSimulationWindow(parent.getSimulationWindow());
        optionPanel = selectedGraphType.getPanel();
        
        optionPanelContainer.add(optionPanel, BorderLayout.CENTER);
    }
    
    private void initLayoutTypeInputs() {
        selectLayoutText = new JLabel(" Select type of layout:");
        selectLayoutText.setFont(Tools.getFont(14));
        selectLayoutText.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        layoutTypeDropdown = new JComboBox<>(LayoutType.values());
        ((JLabel)layoutTypeDropdown.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        layoutTypeDropdown.setFont(Tools.getFont(14));
        layoutTypeDropdown.setVisible(true);
        layoutTypeDropdown.setEnabled(true);
        layoutTypeDropdown.addActionListener(a -> {
           GraphBuilder.layoutType = (LayoutType) layoutTypeDropdown.getSelectedItem();
        });
        
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(100, 10));
        layoutTypePanel.add(spacer);
        layoutTypePanel.add(selectLayoutText);
        layoutTypePanel.add(layoutTypeDropdown);
    }
    
    private void initGraphTypeInputs() {
        selectGraphText = new JLabel(" Select type of graph:");
        selectGraphText.setFont(Tools.getFont(14));
        selectGraphText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        graphTypeDropdown = new JComboBox<>(GraphType.values());
        ((JLabel)graphTypeDropdown.getRenderer()).setHorizontalAlignment(JLabel.CENTER); // center text
//        graphTypeDropdown.setOpaque(true);
        graphTypeDropdown.setFont(Tools.getFont(14));
//        graphTypeDropdown.setBackground(Tools.bgColor);
        graphTypeDropdown.setVisible(true);
        graphTypeDropdown.setEnabled(true);
        graphTypeDropdown.addActionListener(a -> {
            GraphType selectedType = (GraphType) graphTypeDropdown.getSelectedItem();
            selectedType.getPanel().setSimulationWindow(parent.getSimulationWindow());
            setSelectedGraphType(selectedType);
            setNewOptionPanel(selectedType.getPanel());
        });
        
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(100, 10));
        graphTypePanel.add(spacer);
        graphTypePanel.add(selectGraphText);
        graphTypePanel.add(graphTypeDropdown);
        graphTypePanel.add(spacer);
    }
    
    public void initNorth() {
        int initialHeight = 30;
        titlePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D gr = (Graphics2D) g;
                // anti-aliasing
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                gr.setStroke(new BasicStroke(2f));
                g.setColor(Tools.bgColor);
                g.fillRect(0, 0, getWidth(), getHeight());
        
                gr.setColor(Color.BLACK);
                gr.fillRoundRect(75, 89, 346, 50, 12, 12);
                gr.drawRoundRect(75, 34, 346, 102, 12, 12);
                gr.fillRect(75, 80, 346, 20);
                
//                Tools.sleep(1000 / 60);
//                super.repaint();
            }
        };
        titlePanel.setLayout(new BorderLayout());
        titleText1 = new JLabel("Graph  Algorithm", SwingUtilities.CENTER);
        titleText1.setFont(getBoldFont(40));
        titleText1.setVisible(true);
        titlePanel.add(titleText1, BorderLayout.CENTER);
        
        titleText2 = new JLabel("Visualizer", SwingUtilities.CENTER);
        titleText2.setFont(getBoldFont(66));
        titleText2.setForeground(Color.white);
        titleText2.setVisible(true);
        titleText2.setPreferredSize(new Dimension(300, 60));
        titleText2.setVerticalTextPosition(SwingConstants.TOP);
        titlePanel.add(titleText2, BorderLayout.SOUTH);
    
        JLabel dummySpacer = new JLabel(" ");
        dummySpacer.setPreferredSize(new Dimension(100, 30));
        dummySpacer.setOpaque(false);
        titlePanel.add(dummySpacer, BorderLayout.NORTH);
        
        this.add(titlePanel, BorderLayout.NORTH);
        titlePanel.repaint();
    }
    
    public void setNewOptionPanel(OptionPanel panel) {
        optionPanelContainer.remove(optionPanel);
        optionPanel = panel;
        optionPanel.repaint();
//        System.out.println("listeners: " + contButton.getActionListeners().length);
        this.importBtn.removeActionListener(importBtn.getActionListeners()[0]);
        this.importBtn.addActionListener(panel.getButtonAction(selectedGraphType, parent.getFrame()));
        optionPanelContainer.add(optionPanel, BorderLayout.CENTER);
        this.repaint();
        this.updateUI();
    }
    
    public GraphType getSelectedGraphType() { return this.selectedGraphType; }
    
    public void setSelectedGraphType(GraphType type) { this.selectedGraphType = type; }
}
