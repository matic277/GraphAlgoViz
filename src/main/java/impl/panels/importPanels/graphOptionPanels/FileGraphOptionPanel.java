package impl.panels.importPanels.graphOptionPanels;

import impl.graphBuilders.GraphBuilder;
import core.GraphType;
import impl.tools.*;
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
        inputText = new JLabel(" Input path to graph file:");
        inputText.setOpaque(true);
        inputText.setFont(Tools.getFont(14));
        container1.add(inputText);
        
        inputField = new JTextField();
        inputField.setFont(Tools.getFont(14));
        inputField.setText(".\\graphs\\graph1.g6");
        container1.add(inputField);
    
        JPanel container2 = new JPanel();
        container2.setOpaque(false);
        informedNodesText = new JLabel(" Informed nodes:");
        informedNodesText.setOpaque(true);
        informedNodesText.setFont(Tools.getFont(14));
//        informedNodesText.setBackground(Color.red);
        container2.add(informedNodesText);
        
        informedNodesInput = new JTextField();
        informedNodesInput.setPreferredSize(new Dimension(100, informedNodesInput.getPreferredSize().height));
        informedNodesInput.setFont(Tools.getFont(14));
        container2.add(informedNodesInput);
        
       this.add(container1);
       this.add(container2);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        return a -> {
            System.out.println("Listening: " + this.getClass().getSimpleName());
    
            boolean isPercentage = informedNodesInput.getText().contains("%");
            int nodesToInform = isPercentage ?
                    Integer.parseInt(informedNodesInput.getText().replace("%", "")) :
                    Integer.parseInt(informedNodesInput.getText());
            
            GraphBuilder builder = type.getGraphBuilder()
                    .setFileName(inputField.getText())
                    .setInformedProbability(isPercentage ? nodesToInform : null)
                    .setTotalInformed(isPercentage ? null : nodesToInform);
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
