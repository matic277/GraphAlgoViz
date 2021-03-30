package impl.windows;

import core.GraphType;
import core.OptionPanel;
import core.Window;
import impl.listeners.ComboBoxListener;
import impl.graphOptionPanels.RandomGraphOptionPanel;
import impl.listeners.StartupListener;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;

import static impl.tools.Tools.getBoldFont;
import static javax.swing.UIManager.getFont;

public class StartupWindow extends Window {
    
    JLabel titleText1;
    JLabel titleText2;
    JLabel selectGraphText;
    JComboBox<GraphType> dropdown;
    
    OptionPanel optionPanel;
    Point2D optionPanelStartCoordinates;
    
    JButton contButton;
    
    public StartupWindow() {
        super(new Dimension(500, 550));
        
        optionPanelStartCoordinates = new Point(50, 220);
        
        titleText1 = new JLabel("Graph Algorithm", SwingUtilities.CENTER);
        titleText1.setFont(getBoldFont(40));
//        titleText1.setOpaque(true);
//        titleText1.setBackground(GraphicTools.bgColor);
        titleText1.setVisible(true);
        titleText1.setBounds(50, 50, 400, 50);
        this.panel.add(titleText1);
        titleText2 = new JLabel("Vizualizer", SwingUtilities.CENTER);
        titleText2.setFont(getBoldFont(66));
//        titleText2.setOpaque(true);
//        titleText2.setBackground(Color.black);
        titleText2.setForeground(Tools.bgColor);
//        titleText2.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
        titleText2.setVisible(true);
        titleText2.setBounds(85, 102, 331, 55);
        this.panel.add(titleText2);
        
        selectGraphText = new JLabel(" Select type of graph:");
        selectGraphText.setFont(getFont(14));
        selectGraphText.setOpaque(true);
        selectGraphText.setBackground(Tools.bgColor);
        selectGraphText.setVisible(true);
        selectGraphText.setBounds(115, 205, 120, 30);
        this.panel.add(selectGraphText);
        
        dropdown = new JComboBox<>(GraphType.values());
        dropdown.setOpaque(true);
        dropdown.setBounds(235,205, 150, 30);
        dropdown.setFont(getFont(14));
        dropdown.setBackground(Tools.bgColor);
        dropdown.setVisible(true);
        dropdown.setEnabled(true);
        dropdown.addActionListener(new ComboBoxListener(this));
        this.panel.add(dropdown);
        
        optionPanel = new RandomGraphOptionPanel();
        optionPanel.setStartingCoordiantes(optionPanelStartCoordinates);
        this.panel.add(optionPanel);
    
        contButton = new JButton("Continue");
        contButton.setBounds(200, 500, 100, 35);
        contButton.addActionListener(new StartupListener(this));
        this.panel.add(contButton);
        
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
        this.panel.add(optionPanel);
        this.panel.repaint();
        this.panel.updateUI();
    }
    
    public Frame getFrame() { return this.frame; }
    
}
