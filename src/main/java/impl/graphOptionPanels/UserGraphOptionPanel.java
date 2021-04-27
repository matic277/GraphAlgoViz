package impl.graphOptionPanels;

import core.GraphType;
import core.OptionPanel;
import impl.SimulationManager;

import java.awt.*;
import java.awt.event.ActionListener;

public class UserGraphOptionPanel extends OptionPanel {
    
    private static final UserGraphOptionPanel instance = new UserGraphOptionPanel();
    
    public UserGraphOptionPanel() {
        super();
        this.setOpaque(true);
        this.setBackground(Color.red);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        // do nothing, create empty graph
        return a -> {
            System.out.println("Listening: " + this.getClass().getSimpleName());
            new SimulationManager(type.getGraphBuilder());
        };
    }
    
    public static OptionPanel getInstance() {
        return instance;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
