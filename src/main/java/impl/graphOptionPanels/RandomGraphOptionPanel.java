package impl.graphOptionPanels;

import core.OptionPanel;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;

public class RandomGraphOptionPanel extends OptionPanel {
    
    JLabel nodesText, edgedText;
    JTextField nodesInput, edgesInput;
    
    public RandomGraphOptionPanel() {
        super();
        this.setOpaque(true);
        this.setBackground(Color.blue);
        this.repaint();
        
        nodesText = new JLabel("Number of nodes:");
        nodesText.setBounds(50, 80, 110, 30);
        nodesText.setOpaque(true);
        nodesText.setFont(Tools.getFont(12));
//        text.setBackground(Color.red);
        
        nodesInput = new JTextField();
        nodesInput.setBounds(170, 82, 70, 30);
        
        edgedText = new JLabel("Edge probability:");
        edgedText.setBounds(50, 130, 110, 30);
        edgedText.setOpaque(true);
        edgedText.setFont(Tools.getFont(12));
//        text.setBackground(Color.red);
        
        edgesInput = new JTextField();
        edgesInput.setBounds(170, 132, 70, 30);
        edgesInput.setFont(Tools.getFont(12));
        
        this.addComponents(nodesText, edgedText, edgesInput, nodesInput);
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
