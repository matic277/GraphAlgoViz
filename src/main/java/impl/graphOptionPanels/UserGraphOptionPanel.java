package impl.graphOptionPanels;

import core.OptionPanel;

import java.awt.*;

public class UserGraphOptionPanel extends OptionPanel {
    
    
    public UserGraphOptionPanel() {
        super();
        this.setOpaque(true);
        this.setBackground(Color.red);
        
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
