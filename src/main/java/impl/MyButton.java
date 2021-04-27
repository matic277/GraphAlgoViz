package impl;

import impl.tools.Tools;

import javax.swing.*;

public class MyButton extends JButton {
    
    Runnable action;
    
    public MyButton(String text) {
        super(text);
        
//        this.addActionListener(Tools.buttonListener);
    }
    
    public void setOnClickAction(Runnable action) {
        this.action = action;
    }
    
    public Runnable getOnClickAction() {
        return action;
    }
}
