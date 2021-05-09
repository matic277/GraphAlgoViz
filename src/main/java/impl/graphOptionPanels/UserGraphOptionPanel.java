package impl.graphOptionPanels;

import core.GraphType;
import core.OptionPanel;

import java.awt.*;
import java.awt.event.ActionListener;

public class UserGraphOptionPanel extends OptionPanel {
    
    private static final UserGraphOptionPanel instance = new UserGraphOptionPanel();
    
    public UserGraphOptionPanel() {
        super(null);
        this.setOpaque(true);
        this.setBackground(Color.red);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        // do nothing, create empty graph
        return a -> {
            System.out.println("Listening: " + this.getClass().getSimpleName());
            type.getSimulationWindow().onNewGraphImport(type.getGraphBuilder());
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
