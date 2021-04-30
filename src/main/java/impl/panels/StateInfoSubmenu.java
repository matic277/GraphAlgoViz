package impl.panels;

import core.Drawable;
import core.Observer;
import impl.AlgorithmController;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class StateInfoSubmenu implements Drawable, Observer {
    
    final int NUM_OF_STATES = 15;
    final SimulationPanel simPanel;
    
    Rectangle bounds;
    JButton[] stateBox;
    
    final Border DEFAULT_BORDER = new JButton().getBorder();
    final Border SELECTED_BORDER = new LineBorder(Color.RED);
    
    public StateInfoSubmenu(Rectangle bounds, SimulationPanel simPanel) {
        this.bounds = bounds;
        this.simPanel = simPanel;
        this.stateBox = new JButton[NUM_OF_STATES];
        
        int padding = 5;
        int width  = (bounds.width - ((NUM_OF_STATES + 1) * padding)) / NUM_OF_STATES;
        int height = bounds.height - 2 * padding;
        
        for (int i = 0, x = padding; i<NUM_OF_STATES; i++, x+=width+padding) {
            int finalI = i;
            var box = new JButton() { public int index = finalI; };
            box.setText(i+"");
            box.setBounds(
                    x + bounds.x,
                    padding + bounds.y,
                    width,
                    height);
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
            this.simPanel.add(box);
        }
        
//        JLabel lbl = new JLabel("ABC");
//        lbl.setOpaque(true);
//        lbl.setBackground(Color.red);
//        lbl.setVisible(true);
//        stateBox[0].setLayout(null);
//        stateBox[0].add(lbl);
//        lbl.setBounds(0,0,30,lbl.getParent().getBounds().height);
//        lbl.updateUI();
//        stateBox[0].updateUI();
//        stateBox[0].updateUI();
    }
    
    @Override
    public void draw(Graphics2D g, AffineTransform at) {
        g.setColor(Color.WHITE);
        g.fill(bounds);
        g.setColor(Color.black);
        g.draw(bounds);
        
        // outline currently selected one
        g.setStroke(Tools.BOLDER_STROKE);
        g.setColor(Color.red);
//        g.draw(stateBox[AlgorithmController.currentStateIndex].getBounds());
    }
    
    @Override
    public void notifyStateChange(int newStateIndex) {
        for (int i=0; i<stateBox.length; i++) {
            stateBox[i].setBorder(DEFAULT_BORDER);
            if (i <= newStateIndex) stateBox[i].setEnabled(true);
        }
        if (newStateIndex <= stateBox.length-1) {
            stateBox[newStateIndex].setBorder(SELECTED_BORDER);
        }
    }
}
