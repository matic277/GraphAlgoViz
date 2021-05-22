package impl.panels.importPanels.graphOptionPanels;

import impl.graphBuilders.GraphBuilder;
import core.GraphType;
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
        
        JPanel container1 = new JPanel();
        container1.setOpaque(false);
        nodesText = new JLabel(" Number of nodes:");
        nodesText.setOpaque(true);
        nodesText.setBackground(Tools.GRAY);
        nodesText.setFont(Tools.getFont(14));
        
        nodesInput = new JTextField();
        nodesInput.setFont(Tools.getFont(14));
        nodesInput.setPreferredSize(new Dimension(100, nodesInput.getPreferredSize().height));
        container1.add(nodesText);
        container1.add(nodesInput);
    
        JPanel container2 = new JPanel();
        container2.setOpaque(false);
        edgedText = new JLabel(" Edge probability:");
        edgedText.setFont(Tools.getFont(14));
        edgedText.setOpaque(true);
        edgedText.setBackground(Tools.GRAY);
        
        edgesInput = new JTextField();
        edgesInput.setFont(Tools.getFont(14));
        edgesInput.setPreferredSize(new Dimension(100, edgesInput.getPreferredSize().height));
        container2.add(edgedText);
        container2.add(edgesInput);
    
        JPanel container3 = new JPanel();
        container3.setOpaque(false);
        informedNodesText = new JLabel(" Informed nodes:");
        informedNodesText.setFont(Tools.getFont(14));
        informedNodesText.setOpaque(true);
        informedNodesText.setBackground(Tools.GRAY);
        
        informedNodesInput = new JTextField();
        informedNodesInput.setPreferredSize(new Dimension(100, informedNodesInput.getPreferredSize().height));
        informedNodesInput.setFont(Tools.getFont(14));
        container3.add(informedNodesText);
        container3.add(informedNodesInput);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel(" "));
        this.add(container1);
        this.add(container2);
        this.add(container3);
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
            super.simWindow.onNewGraphImport(builder);
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
