package impl.graphOptionPanels;

import core.GraphType;
import core.OptionPanel;

import java.awt.event.ActionListener;

public class NullOptionPanel extends OptionPanel {
    
    public  NullOptionPanel() {
        super();
        
//        this.setOpaque(true);
//        this.setBackground(Color.pink);
//        super.paintPanel();
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        return a -> {
            System.out.println("Listening: " + this.getClass().getSimpleName());
        };
    }
}
