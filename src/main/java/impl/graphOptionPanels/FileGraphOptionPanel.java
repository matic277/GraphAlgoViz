package impl.graphOptionPanels;

import core.GraphBuilder;
import core.GraphType;
import core.OptionPanel;
import impl.SimulationManager;
import impl.tools.*;

import javax.swing.*;
import java.awt.event.ActionListener;


public class FileGraphOptionPanel extends OptionPanel {
    
    JLabel inputText, informedNodesText;
    JTextField inputField, informedNodesInput;
    
    private static final FileGraphOptionPanel instance = new FileGraphOptionPanel();
    
    private FileGraphOptionPanel() {
        super();
        inputText = new JLabel(" Input path to graph file:");
        inputText.setBounds(50, 70, 170, 30);
        inputText.setOpaque(true);
        inputText.setFont(Tools.getFont(12));
//        text.setBackground(Color.red);
        
        inputField = new JTextField();
        inputField.setBounds(47, 100, 300, 30);
        inputField.setFont(Tools.getFont(12));
        inputField.setText(".\\graphs\\graph1.g6");
        
        informedNodesText = new JLabel(" Informed nodes:");
        informedNodesText.setBounds(47, 150, 110, 30);
        informedNodesText.setOpaque(true);
        informedNodesText.setFont(Tools.getFont(12));
//        informedNodesText.setBackground(Color.red);
        
        informedNodesInput = new JTextField();
        informedNodesInput.setBounds(47, 180, informedNodesText.getWidth(), 30);
        informedNodesInput.setFont(Tools.getFont(12));
        
        this.addComponents(inputText, inputField, informedNodesText, informedNodesInput);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        return a -> {
            System.out.println("Listening: " + this.getClass().getSimpleName());
            
            GraphBuilder builder = type.getGraphBuilder()
                    .setFileName(inputField.getText())
                    .setInformedProbability(Double.parseDouble(informedNodesInput.getText()));
            new SimulationManager(builder);
        };
    }
    
    public static FileGraphOptionPanel getInstance() {
        return instance;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
