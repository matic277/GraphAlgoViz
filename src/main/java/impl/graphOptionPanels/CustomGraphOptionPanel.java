package impl.graphOptionPanels;

import core.OptionPanel;
import impl.tools.Tools;

import javax.swing.*;


public class CustomGraphOptionPanel extends OptionPanel {
    
    JLabel text;
    JTextField inputField;
    
    public CustomGraphOptionPanel() {
        super();
        text = new JLabel("Input path to graph file:");
        text.setBounds(50, 100, 170, 30);
        text.setOpaque(true);
        text.setFont(Tools.getFont(12));
//        text.setBackground(Color.red);
        
        inputField = new JTextField();
        inputField.setBounds(47, 130, 300, 30);
        inputField.setFont(Tools.getFont(12));
        
        this.addComponents(text, inputField);
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
}
