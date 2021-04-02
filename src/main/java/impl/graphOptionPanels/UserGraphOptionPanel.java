package impl.graphOptionPanels;

import core.OptionPanel;

import java.awt.*;

public class UserGraphOptionPanel extends OptionPanel {
    
    private static final UserGraphOptionPanel instance = new UserGraphOptionPanel();
    
    public UserGraphOptionPanel() {
        super();
        this.setOpaque(true);
        this.setBackground(Color.red);
        
    }
    
    public static OptionPanel getInstance() {
        return instance;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
