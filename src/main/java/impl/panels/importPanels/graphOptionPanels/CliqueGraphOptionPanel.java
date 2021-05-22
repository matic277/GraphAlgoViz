package impl.panels.importPanels.graphOptionPanels;

import impl.graphBuilders.GraphBuilder;
import core.GraphType;
import impl.graphBuilders.CliqueGraphBuilder;
import impl.tools.Tools;
import impl.windows.ImportGraphWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CliqueGraphOptionPanel extends OptionPanel {
    
    JLabel nodesText, informedNodesText;
    JTextField nodesInput, informedNodesInput;
    
    private static final CliqueGraphOptionPanel instance = new CliqueGraphOptionPanel(null);
    
    public static OptionPanel getInstance() { return instance; }
    
    public CliqueGraphOptionPanel(ImportGraphWindow parent) {
        super(parent);
    
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
        informedNodesText = new JLabel(" Informed nodes:");
        informedNodesText.setFont(Tools.getFont(14));
        informedNodesText.setOpaque(true);
        informedNodesText.setBackground(Tools.GRAY);
    
        informedNodesInput = new JTextField();
        informedNodesInput.setPreferredSize(new Dimension(100, informedNodesInput.getPreferredSize().height));
        informedNodesInput.setFont(Tools.getFont(14));
        container2.add(informedNodesText);
        container2.add(informedNodesInput);
    
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel(" "));
        this.add(container1);
        this.add(container2);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        return a -> {
            boolean isPercentage = informedNodesInput.getText().contains("%");
            int nodesToInform = isPercentage ?
                    Integer.parseInt(informedNodesInput.getText().replace("%", "")) :
                    Integer.parseInt(informedNodesInput.getText());
            
            GraphBuilder builder = new CliqueGraphBuilder()
                    .setNumberOfNodes(Integer.parseInt(nodesInput.getText()))
                    .setInformedProbability(isPercentage ? nodesToInform : null)
                    .setTotalInformed(isPercentage ? null : nodesToInform);
            super.simWindow.onNewGraphImport(builder);
        };
    }
}
