package impl.listeners;

import core.GraphType;
import impl.windows.ImportGraphWindow;
import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboBoxListener implements ActionListener {
    
    SimulationWindow simWindow;
    ImportGraphWindow parent;
    GraphType selectedType = null;
    
    public ComboBoxListener(ImportGraphWindow parent, SimulationWindow simWindow) {
        this.parent = parent;
        this.simWindow = simWindow;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
