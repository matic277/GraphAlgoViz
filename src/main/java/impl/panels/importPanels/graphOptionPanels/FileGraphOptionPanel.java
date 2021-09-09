package impl.panels.importPanels.graphOptionPanels;

import core.GraphType;
import impl.graphBuilders.GraphBuilder;
import impl.nodeinformator.NodeInformatorProperties;
import impl.windows.ImportGraphWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class FileGraphOptionPanel extends OptionPanel {
    
    JLabel inputText, informedNodesText;
    JTextField inputField, informedNodesInput;
    
    private static final FileGraphOptionPanel instance = new FileGraphOptionPanel(null);
    
    private FileGraphOptionPanel(ImportGraphWindow parent) {
        super(parent);
        
        JPanel container1 = new JPanel();
        container1.setOpaque(false);
        container1.setLayout(new BoxLayout(container1, BoxLayout.Y_AXIS));
        inputText = new JLabel("Path to graph file:");
        inputText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(100, 10));
        container1.add(spacer);
        container1.add(inputText);
        
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(300, 30));
        inputField.setText(".\\graphs\\graph1.g6");
        inputField.setHorizontalAlignment(SwingConstants.CENTER);
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        container1.add(inputField);
        JLabel spacer2 = new JLabel();
        spacer2.setPreferredSize(new Dimension(100, 10));
        container1.add(spacer2);
    
        JPanel container2 = new JPanel();
        container2.setOpaque(false);
        container2.setLayout(new BoxLayout(container2, BoxLayout.Y_AXIS));
        informedNodesText = new JLabel("Informed nodes:");
        informedNodesText.setAlignmentX(Component.CENTER_ALIGNMENT);
        container2.add(informedNodesText);
        
        informedNodesInput = new JTextField();
        informedNodesInput.setPreferredSize(new Dimension(120, informedNodesInput.getPreferredSize().height));
        informedNodesInput.setHorizontalAlignment(SwingConstants.CENTER);
        informedNodesInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        container2.add(informedNodesInput);
        
       this.add(container1);
       this.add(container2);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type, JFrame importWindow) {
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
                    .setFileName(inputField.getText())
                    .setInformatorProperties(informatorProperties);
            super.simWindow.onNewGraphImport(builder);
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
