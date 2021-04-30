package impl.listeners;

import core.GraphType;
import impl.windows.StartupWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboBoxListener implements ActionListener {
    
    StartupWindow parent;
    GraphType selectedType = null;
    
    public ComboBoxListener(StartupWindow parent) {
        this.parent = parent;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = String.valueOf(((JComboBox) e.getSource()).getSelectedItem());
        GraphType selectedType = GraphType.getByDescription(text);
//        selectedType = selected;
        parent.setSelectedGraphType(selectedType);
        parent.setNewOptionPanel(selectedType.getPanel());
    }
}
