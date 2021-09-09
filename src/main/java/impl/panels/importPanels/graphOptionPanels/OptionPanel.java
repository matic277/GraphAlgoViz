package impl.panels.importPanels.graphOptionPanels;

import core.GraphType;
import impl.Pair;
import impl.tools.Tools;
import impl.windows.ImportGraphWindow;
import impl.windows.SimulationWindow;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class OptionPanel extends JPanel {
    
    protected ImportGraphWindow parent;
    protected SimulationWindow simWindow;
    
    protected Dimension panelSize;
    List<JComponent> components;
    
    public OptionPanel(ImportGraphWindow parent) {
        this.parent = parent;
//        this.panelSize = new Dimension(300,200);
        this.setVisible(true);
        this.setPreferredSize(panelSize);
        this.setMaximumSize(panelSize);
        
        this.setOpaque(true);
        //this.setBackground(Tools.bgColor);
        
        this.components = new ArrayList<>(10);
        
        //this.setBorder(Tools.UI_BORDER_STANDARD);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D gr = (Graphics2D) g;
        // anti-aliasing
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        //g.setColor(Tools.GRAY3);
        //gr.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        
        gr.setColor(Tools.UI_BORDER_COLOR_STANDARD);
        gr.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
    }
    
    protected void signalBadInput(String errorMsg, JLabel errorLbl) {
        CompletableFuture.runAsync(() -> {
            errorLbl.setText(" " + errorMsg + " ");
            errorLbl.setBorder(new Tools.RoundBorder(Tools.RED, new BasicStroke(2), 10));
            errorLbl.setPreferredSize(new Dimension((int) errorLbl.getPreferredSize().getWidth(), 30));
            errorLbl.setVisible(true);
            Tools.sleep(5000);
            errorLbl.setVisible(false);
        });
    }
    
    protected JLabel getErrorLabel() {
        JLabel lbl = new JLabel();
        lbl.setForeground(Tools.RED);
        lbl.setOpaque(false);
        lbl.setVisible(false);
        return lbl;
    }
    
    public void setSimulationWindow(SimulationWindow simWindow) {
        this.simWindow = simWindow;
    }
    
    public abstract ActionListener getButtonAction(GraphType type, JFrame importWindow);
}
