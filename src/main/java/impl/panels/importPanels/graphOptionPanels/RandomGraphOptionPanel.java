package impl.panels.importPanels.graphOptionPanels;

import core.GraphType;
import impl.graphBuilders.GraphBuilder;
import impl.nodeinformator.NodeInformatorProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RandomGraphOptionPanel extends OptionPanel {
    
    JLabel nodesText, edgedText, informedNodesText;
    JTextField nodesInput, edgesInput, informedNodesInput;
    
    private int textWidth = 125;
    private int inputWidth = 50;
    private int height = 30;
    
    JLabel nodesError;
    JLabel edgesError;
    JLabel informedNodesError;
    
    private static final RandomGraphOptionPanel instance = new RandomGraphOptionPanel();
    
    public RandomGraphOptionPanel() {
        super(null);
    
        nodesError = getErrorLabel();
        edgesError = getErrorLabel();
        informedNodesError = getErrorLabel();
        
        JPanel container1 = new JPanel();
        container1.setOpaque(false);
        nodesText = new JLabel(" Number of nodes:");
        nodesText.setPreferredSize(new Dimension(textWidth, height));
        
        nodesInput = new JTextField();
        //nodesInput.setFont(Tools.getFont(14));
        nodesInput.setPreferredSize(new Dimension(textWidth, height));
        container1.add(nodesText);
        container1.add(nodesInput);
        container1.add(nodesError);
    
        JPanel container2 = new JPanel();
        container2.setOpaque(false);
        edgedText = new JLabel(" Edge probability:");
        edgedText.setPreferredSize(new Dimension(textWidth, height));
        
        edgesInput = new JTextField();
        edgesInput.setPreferredSize(new Dimension(textWidth, height));
        container2.add(edgedText);
        container2.add(edgesInput);
        container2.add(edgesError);
    
        JPanel container3 = new JPanel();
        container3.setOpaque(false);
        informedNodesText = new JLabel(" Informed nodes:");
        informedNodesText.setPreferredSize(new Dimension(textWidth, height));
        
        informedNodesInput = new JTextField();
        informedNodesInput.setPreferredSize(new Dimension(textWidth, height));
        container3.add(informedNodesText);
        container3.add(informedNodesInput);
        container3.add(informedNodesError);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel(" "));
        this.add(container1);
        this.add(container2);
        this.add(container3);
        this.add(Box.createRigidArea(new Dimension(100, 75)));
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type, JFrame importWindow) {
        // read edge probability and number of nodes
        return a -> {
            System.out.println("Listening: " + this.getClass().getSimpleName());
            
            boolean isPercentage = informedNodesInput.getText().contains("%");
            int nodesToInform = isPercentage ?
                    Integer.parseInt(informedNodesInput.getText().replace("%", "")) :
                    Integer.parseInt(informedNodesInput.getText());
    
            // Close window
            importWindow.setVisible(false);
            importWindow.dispose();
            
            NodeInformatorProperties informatorProperties = new NodeInformatorProperties();
            informatorProperties.setTotalNodesToInform(isPercentage ? null : nodesToInform);
            informatorProperties.setInformedProbability(isPercentage ? (double)nodesToInform : null);
            
            GraphBuilder builder = type.getGraphBuilder()
                    .setEdgeProbability(Double.parseDouble(edgesInput.getText()))
                    .setNumberOfNodes(Integer.parseInt(nodesInput.getText()))
                    .setInformatorProperties(informatorProperties);
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
