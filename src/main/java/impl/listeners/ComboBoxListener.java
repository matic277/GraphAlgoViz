package impl.listeners;

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

public class ComboBoxListener implements ActionListener {
    
    StartupWindow parent;
    GraphType selectedType = null;
    
    public ComboBoxListener(StartupWindow parent) {
        this.parent = parent;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = String.valueOf(((JComboBox) e.getSource()).getSelectedItem());
        GraphType selected = GraphType.getByDescription(text);
//        selectedType = selected;
        parent.setNewOptionPanel(selected.getPanel());
    }
}
