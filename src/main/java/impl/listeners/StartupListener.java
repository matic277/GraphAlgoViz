package impl.listeners;

import core.GraphBuilder;
import impl.windows.ImportGraphWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartupListener implements ActionListener {
    
    ImportGraphWindow parent;
    
    public StartupListener(ImportGraphWindow w) {
        this.parent = w;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        parent.getFrame().removeAll();
        parent.getFrame().dispose();
        
        // TODO pass some params here, like graph type
        GraphBuilder builder = parent.getSelectedGraphType().getGraphBuilder();
//        new SimulationManager();
    }
}
