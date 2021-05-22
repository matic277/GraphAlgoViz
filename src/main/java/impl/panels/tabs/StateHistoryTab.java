package impl.panels.tabs;

import core.Observer;
import core.StateObserver;
import impl.AlgorithmController;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class StateHistoryTab extends JPanel implements StateObserver {
    
    final int NUM_OF_STATES = 15;
    
    TabsPanel parent;
    
    JButton[] stateBox;
    
    final Border DEFAULT_BORDER = new JButton().getBorder();
    final Border SELECTED_BORDER = new Tools.RoundBorder(Tools.RED, Tools.BOLDER_STROKE, 10);
    
    public StateHistoryTab(TabsPanel parent) {
        this.parent = parent;
        this.stateBox = new JButton[NUM_OF_STATES];
        
        this.setLayout(new FlowLayout());
        this.setMinimumSize(new Dimension(0, 0));
        this.setBackground(Tools.GRAY3);
        
        int width  = 45;
        int height = 30;
        
        for (int i = 0; i<NUM_OF_STATES; i++) {
            int finalI = i;
            var box = new JButton() { public int index = finalI; };
            box.setText(i+"");
            box.setFont(Tools.getFont(14));
            box.setPreferredSize(new Dimension(width, height));
            box.addActionListener(a -> {
                AlgorithmController.currentStateIndex = box.index;
                box.setBorderPainted(true);
                box.setBorder(SELECTED_BORDER);
                for (JButton btn : stateBox) {
                    if (btn != box) {
                        btn.setBorder(DEFAULT_BORDER);
                    }
                }
            });
            
            // disable all but first button at start
            if (i > 0) box.setEnabled(false);
            
            stateBox[i] = box;
            this.add(box);
        }
        // outline first button
        stateBox[0].setBorder(SELECTED_BORDER);
    }
    
//    @Override
//    public void notifyStateChange(int newStateIndex) {
//        for (int i=0; i<stateBox.length; i++) {
//            stateBox[i].setBorder(DEFAULT_BORDER);
//            stateBox[i].setEnabled(i <= newStateIndex); // enable new buttons
//        }
//        if (newStateIndex <= stateBox.length-1) {
//            stateBox[newStateIndex].setBorder(SELECTED_BORDER);
//        }
//    }
    
    public void setCurrentActiveState(int currentStateIndex) {
        for (int i=0; i<stateBox.length; i++) {
            stateBox[i].setBorder(DEFAULT_BORDER);
            if (AlgorithmController.totalStates < i) break;
        }
        
        stateBox[currentStateIndex].setBorder(SELECTED_BORDER);
        
        for (int i=AlgorithmController.totalStates; i<stateBox.length; i++) stateBox[i].setEnabled(false);
    }
    
    public void onNewGraphImport() {
        // disable all but first button (first state)
        for (int i=1; i<stateBox.length; i++) {
            stateBox[i].setBorder(DEFAULT_BORDER);
            stateBox[i].setEnabled(false);
        }
        stateBox[0].setBorder(SELECTED_BORDER);
        stateBox[0].setEnabled(true);
    }
    
    @Override
    public void onStateChange() {
        for (int i=0; i<stateBox.length; i++) {
            stateBox[i].setBorder(DEFAULT_BORDER);
            stateBox[i].setEnabled(i <= AlgorithmController.currentStateIndex); // enable new buttons
        }
        if (AlgorithmController.currentStateIndex <= stateBox.length-1) {
            stateBox[AlgorithmController.currentStateIndex].setBorder(SELECTED_BORDER);
        }
    }
    
    @Override
    public void onTotalStateChange() {
        for (int i=1; i<stateBox.length; i++) {
            stateBox[i].setEnabled(i < AlgorithmController.totalStates);
        }
    }
}
