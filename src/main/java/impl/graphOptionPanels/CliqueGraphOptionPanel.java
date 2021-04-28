package impl.graphOptionPanels;

import core.GraphBuilder;
import core.GraphType;
import core.OptionPanel;
import impl.MyButton;
import impl.SimulationManager;
import impl.graphBuilders.CliqueGraphBuilder;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CliqueGraphOptionPanel extends OptionPanel {
    
    JLabel text;
    JTextField inputField;
    
    private static final CliqueGraphOptionPanel instance = new CliqueGraphOptionPanel();
    
    public static OptionPanel getInstance() { return instance; }
    
    public CliqueGraphOptionPanel() {
        super();
        text = new JLabel(" Number of nodes: ");
        text.setBounds(50, 100, 150, 30);
        text.setOpaque(true);
        text.setFont(Tools.getFont(12));
//        text.setBackground(Color.red);
        
        inputField = new JTextField();
        inputField.setBounds(210, 100, 100, 30);
        inputField.setFont(Tools.getFont(12));
    
        this.addComponents(text, inputField);
    }
    
    
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        return a -> {
            GraphBuilder builder = new CliqueGraphBuilder()
                    .setNumberOfNodes(Integer.parseInt(inputField.getText()));
            new SimulationManager(builder);
        };
    }
}
