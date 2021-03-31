package impl;

import core.ClickAction;
import impl.tools.Tools;

import javax.swing.*;

public class Button extends JButton {
    
    ClickAction action;
    
    public Button(String text) {
        super(text);
        this.addActionListener(Tools.buttonListener);
    }
    
    public void setOnClickAction(ClickAction action) {
        this.action = action;
    }
    
    public ClickAction getOnClickAction() {
        return action;
    }
}
