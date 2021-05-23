package impl.panels.importPanels.graphOptionPanels;

import impl.Pair;
import impl.graphBuilders.GraphBuilder;
import core.GraphType;
import impl.graphBuilders.CliqueGraphBuilder;
import impl.tools.Tools;
import impl.windows.ImportGraphWindow;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;

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
//        nodesText.setOpaque(true);
//        nodesText.setBackground(Tools.GRAY);
        nodesText.setFont(Tools.getFont(14));
        nodesText.setPreferredSize(new Dimension(textWidth, height));
    
        nodesInput = new JTextField();
        nodesInput.setFont(Tools.getFont(14));
        nodesInput.setPreferredSize(new Dimension(inputWidth, height));
        container1.add(nodesText);
        container1.add(nodesInput);
        container1.add(nodesError);
    
        JPanel container2 = new JPanel();
        container2.setOpaque(false);
        informedNodesText = new JLabel("Informed nodes:");
        informedNodesText.setFont(Tools.getFont(14));
//        informedNodesText.setOpaque(true);
//        informedNodesText.setBackground(Tools.GRAY);
        informedNodesText.setPreferredSize(new Dimension(textWidth, height));
    
        informedNodesInput = new JTextField();
        informedNodesInput.setPreferredSize(new Dimension(inputWidth, height));
        informedNodesInput.setFont(Tools.getFont(14));
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
                numberOfNodes = performInputValidationInteger(nodesInput.getText());
            } catch (RuntimeException re) {
                signalBadInput(re.getLocalizedMessage(), nodesError);
                return;
            }
            
            Pair<Number, Boolean> informedInfo;
            try {
                informedInfo = performInputValidationDoubleOrInteger(informedNodesInput.getText());
            } catch (RuntimeException re) {
                signalBadInput(re.getLocalizedMessage(), informedNodesError);
                return;
            }
            boolean isPercentage = informedInfo.getB();
            Number informedNodes = informedInfo.getA();
            
            // Close window
            importWindow.setVisible(false);
            importWindow.dispose();
            
            GraphBuilder builder = new CliqueGraphBuilder()
                    .setNumberOfNodes(numberOfNodes)
                    .setInformedProbability(isPercentage ? informedNodes.doubleValue() : null)
                    .setTotalInformed(isPercentage ? null : informedNodes.intValue());
            super.simWindow.onNewGraphImport(builder);
        };
    }
}
