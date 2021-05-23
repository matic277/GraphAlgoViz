package impl.panels.importPanels.graphOptionPanels;

import core.GraphType;
import impl.graphBuilders.EmptyGraphBuilder;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UserGraphOptionPanel extends OptionPanel {
    
    private static final UserGraphOptionPanel instance = new UserGraphOptionPanel();
    
    public UserGraphOptionPanel() {
        super(null);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type, JFrame importWindow) {
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
