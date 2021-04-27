package impl.listeners;

import impl.MyButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof MyButton btn) {
            btn.getOnClickAction().run();
        } else {
            System.out.println("Unrecognized command");
        }
    }
}
