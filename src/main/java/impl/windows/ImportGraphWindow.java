package impl.windows;

import core.GraphType;
import core.OptionPanel;
import core.Window;
import impl.listeners.ComboBoxListener;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

import static impl.tools.Tools.getBoldFont;
import static javax.swing.UIManager.getFont;

public class ImportGraphWindow extends Window {
    
    SimulationWindow parent;
    
    JLabel titleText1;
    JLabel titleText2;
    JLabel selectGraphText;
    JComboBox<GraphType> dropdown;
    GraphType selectedGraphType;
    
    OptionPanel optionPanel;
    Point2D optionPanelStartCoordinates;
    
    JButton importBtn;
    
    public ImportGraphWindow(SimulationWindow parent) {
        super(new Dimension(500, 550));
        this.parent = parent;
        
        optionPanelStartCoordinates = new Point(50, 220);
        
        titleText1 = new JLabel("Graph Algorithm", SwingUtilities.CENTER);
        titleText1.setFont(getBoldFont(40));
//        titleText1.setOpaque(true);
//        titleText1.setBackground(GraphicTools.bgColor);
        titleText1.setVisible(true);
        titleText1.setBounds(50, 50, 400, 50);
        this.panel.add(titleText1);
        titleText2 = new JLabel("Visualizer", SwingUtilities.CENTER);
        titleText2.setFont(getBoldFont(66));
//        titleText2.setOpaque(true);
//        titleText2.setBackground(Color.black);
        titleText2.setForeground(Tools.bgColor);
//        titleText2.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
        titleText2.setVisible(true);
        titleText2.setBounds(85, 102, 331, 55);
        this.panel.add(titleText2);
        
        selectGraphText = new JLabel(" Select type of graph:");
        selectGraphText.setFont(Tools.getFont(12));
        selectGraphText.setOpaque(true);
        selectGraphText.setBackground(Tools.bgColor);
        selectGraphText.setVisible(true);
        selectGraphText.setBounds(113, 205, 127, 30);
        this.panel.add(selectGraphText);
        
        dropdown = new JComboBox<>(GraphType.values());
        dropdown.setOpaque(true);
        dropdown.setBounds(240,205, 150, 30);
        dropdown.setFont(getFont(14));
        dropdown.setBackground(Tools.bgColor);
        dropdown.setVisible(true);
        dropdown.setEnabled(true);
        dropdown.addActionListener(new ComboBoxListener(this, parent));
        this.panel.add(dropdown);
        selectedGraphType = GraphType.STATIC_TEST;
        selectedGraphType.getPanel().setSimulationWindow(parent);
        
        optionPanel = selectedGraphType.getPanel();
        optionPanel.setStartingCoordiantes(optionPanelStartCoordinates);
        this.panel.add(optionPanel);
        
        importBtn = new JButton("Import");
        importBtn.setFont(Tools.getFont(14));
        importBtn.setBounds(200, 500, 100, 35);
        importBtn.addActionListener(a -> {
            // default behaviour
            this.getFrame().removeAll();
            this.getFrame().dispose();
        });
        importBtn.addActionListener(optionPanel.getButtonAction(selectedGraphType));
        
        this.panel.add(importBtn);
        
        // test
//        JLabel l = new JLabel();
//        l.setBounds(0, 10, 500, 10);
//        l.setOpaque(true);
//        l.setBackground(Color.red);
//        this.panel.add(l);
        
        this.panel.updateUI();
    }
    
    public void setNewOptionPanel(OptionPanel panel) {
        this.panel.remove(optionPanel);
        optionPanel = panel;
        optionPanel.setStartingCoordiantes(optionPanelStartCoordinates);
        optionPanel.repaint();
//        System.out.println("listeners: " + contButton.getActionListeners().length);
        this.importBtn.removeActionListener(importBtn.getActionListeners()[0]);
        this.importBtn.addActionListener(panel.getButtonAction(selectedGraphType));
        this.panel.add(optionPanel);
        this.panel.repaint();
        this.panel.updateUI();
    }
    
    public GraphType getSelectedGraphType() { return this.selectedGraphType; }
    public void setSelectedGraphType(GraphType type) { this.selectedGraphType = type; }
    public Frame getFrame() { return this.frame; }
    
}
