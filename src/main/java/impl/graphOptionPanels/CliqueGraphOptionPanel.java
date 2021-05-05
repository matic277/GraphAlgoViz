package impl.graphOptionPanels;

import core.GraphBuilder;
import core.GraphType;
import core.OptionPanel;
import impl.SimulationManager;
import impl.graphBuilders.CliqueGraphBuilder;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CliqueGraphOptionPanel extends OptionPanel {
    
    JLabel inputText, informedNodesText;
    JTextField inputField, informedNodesInput;
    
    private static final CliqueGraphOptionPanel instance = new CliqueGraphOptionPanel();
    
    public static OptionPanel getInstance() { return instance; }
    
    public CliqueGraphOptionPanel() {
        super();
        inputText = new JLabel(" Number of nodes: ");
        inputText.setBounds(50, 100, 110, 30);
        inputText.setOpaque(true);
        inputText.setFont(Tools.getFont(12));
//        text.setBackground(Color.red);
        
        inputField = new JTextField();
        inputField.setBounds(180, 100, 80, 30);
        inputField.setFont(Tools.getFont(12));
        
        informedNodesText = new JLabel(" Informed nodes:");
        informedNodesText.setBounds(50, 150, 110, 30);
        informedNodesText.setOpaque(true);
        informedNodesText.setFont(Tools.getFont(12));
//        informedNodesText.setBackground(Color.red);
        
        informedNodesInput = new JTextField();
        informedNodesInput.setBounds(180, 150, 80, 30);
        informedNodesInput.setFont(Tools.getFont(12));
    
        this.addComponents(inputText, inputField, informedNodesText, informedNodesInput);
    }
    
    
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        return a -> {
            GraphBuilder builder = new CliqueGraphBuilder()
                    .setNumberOfNodes(Integer.parseInt(inputField.getText()))
                    .setInformedProbability(Double.parseDouble(informedNodesInput.getText()));
            new SimulationManager(builder);
        };
    }
}
