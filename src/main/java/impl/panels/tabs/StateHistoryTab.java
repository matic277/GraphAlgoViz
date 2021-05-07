package impl.panels.tabs;

import core.Observer;
import impl.AlgorithmController;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class StateHistoryTab extends JPanel implements Observer {
    
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
        
        int padding = 5;
        int width  = 45;
        int height = 30; // ?? flowlayout
        
        //for (int i = 0, x = padding; i<NUM_OF_STATES; i++, x+=width+padding) { // when bounds is a rectangle and we draw it manually
        for (int i = 0; i<NUM_OF_STATES; i++) { // when the bound is a jlabel
            int finalI = i;
            var box = new JButton() { public int index = finalI; };
            box.setText(i+"");
            box.setFont(Tools.getFont(14));
//            box.setBounds( // rectangle bound
//                    x + bounds.x,
//                    padding + bounds.y,
//                    width,
//                    height);
            box.setPreferredSize(new Dimension(width, height)); // bounded by jlabel with flowlayout
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
    
    
    @Override
    public void notifyStateChange(int newStateIndex) {
        for (int i=0; i<stateBox.length; i++) {
            stateBox[i].setBorder(DEFAULT_BORDER);
            if (i <= newStateIndex) stateBox[i].setEnabled(true);
            else stateBox[i].setEnabled(false);
        }
        if (newStateIndex <= stateBox.length-1) {
            stateBox[newStateIndex].setBorder(SELECTED_BORDER);
        }
    }
    
    public static class RoundBorder implements Border {
        Color clr;
        private final int rad;
        public RoundBorder(Color clr, int rad) { this.rad = rad; this.clr = clr; }
        
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(clr);
//            ((Graphics2D) g).setStroke(Tools.BOLD_STROKE);
            g.drawRoundRect(x+1, y+1, width-3, height-3, rad, rad);
        }
        public boolean isBorderOpaque() { return true; }
        public Insets getBorderInsets(Component c) { return new Insets(this.rad+1, this.rad+1, this.rad+2, this.rad); }
    }
    
}
