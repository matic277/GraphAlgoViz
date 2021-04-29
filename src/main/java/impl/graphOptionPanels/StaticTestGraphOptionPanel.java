package impl.graphOptionPanels;

import core.GraphBuilder;
import core.GraphType;
import core.OptionPanel;
import impl.SimulationManager;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StaticTestGraphOptionPanel extends OptionPanel {
    
    private static final StaticTestGraphOptionPanel instance = new StaticTestGraphOptionPanel();
    
    private StaticTestGraphOptionPanel() {
        super();
    
        JLabel lbl = new JLabel("DEBUG: Load predefined graph");
        lbl.setOpaque(true);
        lbl.setBackground(Color.red);
        lbl.setBounds(80, 80, 210, 30);
        lbl.setFont(Tools.getFont(12));
        
        JLabel inner = new JLabel("INNER");
        inner.setOpaque(true);
        inner.setBackground(Color.BLUE);
        inner.setBounds(10, 10, 30, 30);
        inner.setFont(Tools.getFont(12));
        inner.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                System.out.println("CLICK");
            }
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
            @Override public void mouseEntered(MouseEvent e) { }
            @Override public void mouseExited(MouseEvent e) { }
        });
        
        lbl.add(inner);
        
        
        this.add(lbl);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        return a -> {
            System.out.println("Listening: " + this.getClass().getSimpleName());
            
            // do nothing
            GraphBuilder builder = type.getGraphBuilder();
            new SimulationManager(builder);
        };
    }
    
    public static StaticTestGraphOptionPanel getInstance() {
        return instance;
    }
}
