package impl.panels.tabs;

import core.Observer;
import impl.AlgorithmController;
import impl.panels.SimulationPanel;
import impl.panels.StateInfoSubmenu;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class StateHistoryTab extends JPanel implements Observer {
    
    final int NUM_OF_STATES = 15;
//    final SimulationPanel simPanel;
    
    TabsPanel parent;
    
//    JLabel infoText;
    JLabel boundsLbl;
    JButton[] stateBox;
    
    final Border DEFAULT_BORDER = new JButton().getBorder();
    final Border SELECTED_BORDER = new StateInfoSubmenu.RoundBorder(Tools.RED, 10);
    
    public StateHistoryTab(TabsPanel parent) {
        this.parent = parent;
        this.stateBox = new JButton[NUM_OF_STATES];
        
        this.setLayout(new FlowLayout());
        
//        infoText = new JLabel(" History of states by rounds");
//        infoText.setBackground(Color.white);
//        infoText.setFont(Tools.getBoldFont(14));
//        infoText.setBorder(new LineBorder(Color.black, 2));
//        infoText.setOpaque(true);
//        infoText.setBounds(
//                bounds.x,
//                bounds.y - 30,
//                200,
//                30);
//        this.add(infoText);
//
//        boundsLbl = new JLabel();
//        boundsLbl.setBackground(Color.white);
//        boundsLbl.setBorder(new LineBorder(Color.black, 2));
//        boundsLbl.setOpaque(true);
//        boundsLbl.setBounds(bounds);
//        boundsLbl.setLayout(new FlowLayout());
//        this.add(boundsLbl);
        
        
        int padding = 5;
        int width  = 45;
        int height = 30; // ?? flowlayout
        
        //for (int i = 0, x = padding; i<NUM_OF_STATES; i++, x+=width+padding) { // when bounds is a rectangle and we draw it manually
        for (int i = 0; i<NUM_OF_STATES; i++) { // when the bound is a jlabel
            int finalI = i;
            var box = new JButton() { public int index = finalI; };
            box.setText(i+"");
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
