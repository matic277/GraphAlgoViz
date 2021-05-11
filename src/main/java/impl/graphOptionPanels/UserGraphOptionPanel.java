package impl.graphOptionPanels;

import core.GraphType;
import core.OptionPanel;
import impl.graphBuilders.EmptyGraphBuilder;

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
            super.simWindow.onNewGraphImport(new EmptyGraphBuilder());
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
