package impl.panels.importPanels.graphOptionPanels;

import impl.Pair;
import impl.graphBuilders.GraphBuilder;
import core.GraphType;
import impl.graphBuilders.CliqueGraphBuilder;
import impl.nodeinformator.NodeInformatorProperties;
import impl.tools.InputHelpers;
import impl.windows.ImportGraphWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CliqueGraphOptionPanel extends OptionPanel {
    
    JLabel nodesText, informedNodesText;
    JTextField nodesInput, informedNodesInput;
    
    JLabel nodesError;
    JLabel informedNodesError;
    
    private int textWidth = 125;
    private int inputWidth = 75;
    private int height = 30;
    
    private static final CliqueGraphOptionPanel instance = new CliqueGraphOptionPanel(null);
    
    public static OptionPanel getInstance() { return instance; }
    
    public CliqueGraphOptionPanel(ImportGraphWindow parent) {
        super(parent);
    
        nodesError = getErrorLabel();
        informedNodesError = getErrorLabel();
        
        JPanel container1 = new JPanel();
        container1.setOpaque(false);
        nodesText = new JLabel("Number of nodes:");
        nodesText.setPreferredSize(new Dimension(textWidth, height));
    
        nodesInput = new JTextField();
        //nodesInput.setFont(Tools.getFont(14));
        nodesInput.setPreferredSize(new Dimension(inputWidth, height));
        container1.add(nodesText);
        container1.add(nodesInput);
        container1.add(nodesError);
    
        JPanel container2 = new JPanel();
        container2.setOpaque(false);
        informedNodesText = new JLabel("Informed nodes:");
        informedNodesText.setPreferredSize(new Dimension(textWidth, height));
    
        informedNodesInput = new JTextField();
        informedNodesInput.setPreferredSize(new Dimension(inputWidth, height));
        container2.add(informedNodesText);
        container2.add(informedNodesInput);
        container2.add(informedNodesError);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel(" "));
        this.add(container1);
        this.add(container2);
        this.add(Box.createRigidArea(new Dimension(100, 140)));
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type, JFrame importWindow) {
        return a -> {
            int numberOfNodes;
            try {
                numberOfNodes = InputHelpers.performInputValidationInteger(nodesInput.getText());
            } catch (RuntimeException re) {
                signalBadInput(re.getLocalizedMessage(), nodesError);
                return;
            }
            
            Pair<Number, Boolean> informedInfo;
            try {
                informedInfo = InputHelpers.performInputValidationDoubleOrInteger(informedNodesInput.getText());
            } catch (RuntimeException re) {
                signalBadInput(re.getLocalizedMessage(), informedNodesError);
                return;
            }
            boolean isPercentage = informedInfo.getB();
            Number informedNodes = informedInfo.getA();
            
            // Close window
            importWindow.setVisible(false);
            importWindow.dispose();
    
            NodeInformatorProperties informatorProperties = new NodeInformatorProperties();
            informatorProperties.setTotalNodesToInform(isPercentage ? null : informedNodes.intValue());
            informatorProperties.setInformedProbability(isPercentage ? informedNodes.doubleValue() : null);
            
            GraphBuilder builder = new CliqueGraphBuilder()
                    .setNumberOfNodes(numberOfNodes) // TODO is this necessary?
                    .setInformatorProperties(informatorProperties);
            super.simWindow.onNewGraphImport(builder);
        };
    }
}
