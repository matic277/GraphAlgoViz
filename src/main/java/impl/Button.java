package impl;

import impl.tools.Tools;

import javax.swing.*;

public class Button extends JButton {
    
    Runnable action;
    
    public Button(String text) {
        super(text);
        this.addActionListener(Tools.buttonListener);
    }
    
    public void setOnClickAction(Runnable action) {
        this.action = action;
    }
    
    public Runnable getOnClickAction() {
        return action;
    }
}
