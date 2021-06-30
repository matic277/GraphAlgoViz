package impl.panels.tabs;

import core.Observer;
import core.StateObserver;
import impl.AlgorithmController;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StateHistoryTab extends JPanel implements StateObserver {
    
    final int NUM_OF_STATES = 50;
    
    TabsPanel parent;
    
    JButton highlightedBtn;
    List<JButton> stateList;
    
    final Border DEFAULT_BORDER = new JButton().getBorder();
    final Border SELECTED_BORDER = new Tools.RoundBorder(Tools.RED, Tools.BOLDER_STROKE, 10);
    
    public StateHistoryTab(TabsPanel parent) {
        this.parent = parent;
        this.stateList = new ArrayList<>(50);
        
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setMinimumSize(new Dimension(0, 0));
        this.setBackground(Tools.GRAY3);
        
        JButton firstStateBtn = getNewStateButton(0);
        firstStateBtn.setBorder(SELECTED_BORDER);
        highlightedBtn = firstStateBtn;
        this.stateList.add(firstStateBtn);
        this.add(firstStateBtn);
    }
    
    private JButton getNewStateButton(int stateIndex) {
        int width  = 50;
        int height = 30;
        JButton btn = new JButton(stateIndex+"");
        btn.setPreferredSize(new Dimension(width, height));
        btn.setFont(Tools.getMonospacedFont(14));
        btn.addActionListener(a -> {
            AlgorithmController.currentStateIndex = stateIndex;
            btn.setBorderPainted(true);
            btn.setBorder(SELECTED_BORDER);
            
            // TODO:
            // This is NOT thread-safe!
            // controller invokes onXYZ() change methods in
            // this class, and changes buttons as-well!
            
            // Probable solution:
            // While the algorithm is executing, these buttons
            // shouldn't be selectable anyways!
            
            // when a button is pressed, un-highlight the other
            highlightedBtn.setBorder(DEFAULT_BORDER);
            highlightedBtn = btn;
        });
        System.out.println("new state button: " + stateIndex);
        return btn;
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
        highlightedBtn.setBorder(DEFAULT_BORDER);
        stateList.get(currentStateIndex).setBorder(SELECTED_BORDER);
        highlightedBtn = stateList.get(currentStateIndex);
        
        this.repaint();
    }
    
    public void onNewGraphImport() {
        JButton firstButton = stateList.get(0);
        firstButton.setBorder(SELECTED_BORDER);
        highlightedBtn = firstButton;
        stateList.clear();
        stateList.add(firstButton);
        this.removeAll();
        this.add(firstButton);
        
        this.repaint();
    }
    
    @Override
    public void onStateChange() {
        System.out.println("state change");
        highlightedBtn.setBorder(DEFAULT_BORDER);
        
        JButton newBtn = getNewStateButton(AlgorithmController.currentStateIndex);
        newBtn.setBorder(SELECTED_BORDER);
        
        stateList.add(newBtn);
        this.add(newBtn);
        highlightedBtn = newBtn;
        
        this.repaint();
    }
    
    @Override
    public void onTotalStateChange() {
        throw new RuntimeException("TOTAL STATE CHANGE");
    }
    
    public void deleteFutureHistory() {
        int indexToDeleteFromOn = 0;
        for (int i=0; i<stateList.size(); i++) {
            if (stateList.get(i) == highlightedBtn) {
                indexToDeleteFromOn = i + 1;
                break;
            }
        }
        for (int i=indexToDeleteFromOn; i<stateList.size(); i++) {
            this.remove(stateList.get(i));
        }
        
        this.repaint();
    }
}
