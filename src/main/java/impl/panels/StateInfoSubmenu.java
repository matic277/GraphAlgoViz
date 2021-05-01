package impl.panels;

import core.Drawable;
import core.Observer;
import impl.AlgorithmController;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class StateInfoSubmenu implements Drawable, Observer {
    
    final int NUM_OF_STATES = 15;
    final SimulationPanel simPanel;
    
    JLabel infoText;
    JLabel boundsLbl;
    Rectangle bounds;
    JButton[] stateBox;
    
    final Border DEFAULT_BORDER = new JButton().getBorder();
    final Border SELECTED_BORDER = new RoundBorder(Tools.RED, 10);
    
    public StateInfoSubmenu(Rectangle bounds, SimulationPanel simPanel) {
        this.bounds = bounds;
        this.simPanel = simPanel;
        this.stateBox = new JButton[NUM_OF_STATES];
        
        JLabel WHITE = new JLabel(); // hides borders between text and bound labels, must be added first
        WHITE.setBounds(bounds.x + 2, bounds.y-5, 200-4, 10);
        WHITE.setOpaque(true);
        WHITE.setBackground(Color.WHITE);
        this.simPanel.add(WHITE);
        
        infoText = new JLabel(" History of states by rounds");
        infoText.setBackground(Color.white);
        infoText.setFont(Tools.getBoldFont(14));
        infoText.setBorder(new LineBorder(Color.black, 2));
        infoText.setOpaque(true);
        infoText.setBounds(
                bounds.x,
                bounds.y - 30,
                200,
                30);
        this.simPanel.add(infoText);
    
        boundsLbl = new JLabel();
        boundsLbl.setBackground(Color.white);
        boundsLbl.setBorder(new LineBorder(Color.black, 2));
        boundsLbl.setOpaque(true);
        boundsLbl.setBounds(bounds);
        boundsLbl.setLayout(new FlowLayout());
        this.simPanel.add(boundsLbl);
        
        
        int padding = 5;
        int width  = (bounds.width - ((NUM_OF_STATES + 1) * padding)) / NUM_OF_STATES;
        int height = bounds.height - 3 * padding; // ?? flowlayout
    
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
            this.boundsLbl.add(box);
        }
        // outline first button
        stateBox[0].setBorder(SELECTED_BORDER);
        
        
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
        // TODO remove?
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
    
    private static class RoundBorder implements Border {
        Color clr;
        private final int rad;
        RoundBorder(Color clr, int rad) { this.rad = rad; this.clr = clr; }
        
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(clr);
            g.drawRoundRect(x+1, y+1, width-3, height-3, rad, rad);
        }
        public boolean isBorderOpaque() { return true; }
        public Insets getBorderInsets(Component c) { return new Insets(this.rad+1, this.rad+1, this.rad+2, this.rad); }
    }
}
