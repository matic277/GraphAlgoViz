package impl.panels.importPanels.graphOptionPanels;

import core.GraphType;

import java.awt.event.ActionListener;

public class NullOptionPanel extends OptionPanel {
    
    public  NullOptionPanel() {
        super(null);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        return a -> {
            System.out.println("Listening: " + this.getClass().getSimpleName());
        };
    }
}
