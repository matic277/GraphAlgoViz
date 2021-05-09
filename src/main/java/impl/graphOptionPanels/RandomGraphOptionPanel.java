package impl.graphOptionPanels;

import core.GraphBuilder;
import core.GraphType;
import core.OptionPanel;
import impl.SimulationManager;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RandomGraphOptionPanel extends OptionPanel {
    
    JLabel nodesText, edgedText, informedNodesText;
    JTextField nodesInput, edgesInput, informedNodesInput;
    
    private static final RandomGraphOptionPanel instance = new RandomGraphOptionPanel();
    
    public RandomGraphOptionPanel() {
        super(null);
        this.setOpaque(true);
        this.setBackground(Color.blue);
        this.repaint();
        
        nodesText = new JLabel(" Number of nodes:");
        nodesText.setBounds(100, 80, 110, 30);
        nodesText.setOpaque(true);
        nodesText.setFont(Tools.getFont(12));
//        text.setBackground(Color.red);
        
        nodesInput = new JTextField();
        nodesInput.setBounds(220, 82, 70, 30);
        
        edgedText = new JLabel(" Edge probability:");
        edgedText.setBounds(100, 130, 110, 30);
        edgedText.setOpaque(true);
        edgedText.setFont(Tools.getFont(12));
//        text.setBackground(Color.red);
        
        edgesInput = new JTextField();
        edgesInput.setBounds(220, 132, 70, 30);
        edgesInput.setFont(Tools.getFont(12));
        
        informedNodesText = new JLabel(" Informed nodes:");
        informedNodesText.setBounds(100, 180, 110, 30);
        informedNodesText.setOpaque(true);
        informedNodesText.setFont(Tools.getFont(12));
//        informedNodesText.setBackground(Color.red);
        
        informedNodesInput = new JTextField();
        informedNodesInput.setBounds(220, 180, 70, 30);
        informedNodesInput.setFont(Tools.getFont(12));
        
        this.addComponents(nodesText, edgedText, edgesInput, nodesInput, informedNodesText, informedNodesInput);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        // read edge probability and number of nodes
        return a -> {
            System.out.println("Listening: " + this.getClass().getSimpleName());
            
            boolean isPercentage = informedNodesInput.getText().contains("%");
            int nodesToInform = isPercentage ?
                    Integer.parseInt(informedNodesInput.getText().replace("%", "")) :
                    Integer.parseInt(informedNodesInput.getText());
            
            GraphBuilder builder = type.getGraphBuilder()
                    .setEdgeProbability(Double.parseDouble(edgesInput.getText()))
                    .setNumberOfNodes(Integer.parseInt(nodesInput.getText()))
                    .setInformedProbability(isPercentage ? nodesToInform : null)
                    .setTotalInformed(isPercentage ? null : nodesToInform);
            type.getSimulationWindow().onNewGraphImport(builder);
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
