package impl;

import core.GraphType;
import core.OptionPanel;
import impl.graphOptionPanels.CustomGraphOptionPanel;
import impl.graphOptionPanels.RandomGraphOptionPanel;
import impl.graphOptionPanels.UserGraphOptionPanel;
import impl.windows.StartupWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ComboBoxListener implements ActionListener {
    
    Map<GraphType, OptionPanel> dropdownPanelMap;
    StartupWindow parent;
    
    GraphType selectedType = null;
    
    public ComboBoxListener(StartupWindow parent) {
        this.parent = parent;
        dropdownPanelMap = new HashMap<>();
        dropdownPanelMap.put(GraphType.CUSTOM, new CustomGraphOptionPanel());
        dropdownPanelMap.put(GraphType.RANDOM, new RandomGraphOptionPanel());
        dropdownPanelMap.put(GraphType.USER, new UserGraphOptionPanel());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = String.valueOf(((JComboBox) e.getSource()).getSelectedItem());
        GraphType selected = GraphType.getByDescription(text);
        OptionPanel newPanel = dropdownPanelMap.get(selected);
        selectedType = selected;
        parent.setNewOptionPanel(newPanel);
    }
}
