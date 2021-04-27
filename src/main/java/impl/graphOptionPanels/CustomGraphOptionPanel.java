package impl.graphOptionPanels;

import core.GraphBuilder;
import core.GraphType;
import core.OptionPanel;
import impl.SimulationManager;
import impl.tools.*;

import javax.swing.*;
import java.awt.event.ActionListener;


public class CustomGraphOptionPanel extends OptionPanel {
    
    JLabel text;
    JTextField inputField;
    
    private static final CustomGraphOptionPanel instance = new CustomGraphOptionPanel();
    
    private CustomGraphOptionPanel() {
        super();
        text = new JLabel(" Input path to graph file:");
        text.setBounds(50, 100, 170, 30);
        text.setOpaque(true);
        text.setFont(Tools.getFont(12));
//        text.setBackground(Color.red);
        
        inputField = new JTextField();
        inputField.setBounds(47, 130, 300, 30);
        inputField.setFont(Tools.getFont(12));
        
        this.addComponents(text, inputField);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        return a -> {
            System.out.println("Hello from " + this.getClass().getSimpleName());
            
            GraphBuilder builder = type.getGraphBuilder()
                    .setFileName(inputField.getText());
            new SimulationManager(builder);
        };
    }
    
    public static CustomGraphOptionPanel getInstance() {
        return instance;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
}
