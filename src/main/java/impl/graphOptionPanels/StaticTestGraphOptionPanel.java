package impl.graphOptionPanels;

import core.GraphBuilder;
import core.GraphType;
import core.OptionPanel;
import impl.SimulationManager;
import impl.tools.Tools;
import impl.windows.ImportGraphWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StaticTestGraphOptionPanel extends OptionPanel {
    
    private static final StaticTestGraphOptionPanel instance = new StaticTestGraphOptionPanel(null);
    
    private StaticTestGraphOptionPanel(ImportGraphWindow parent) {
        super(parent);
    
        JLabel lbl = new JLabel("DEBUG: Load predefined graph");
        lbl.setOpaque(true);
        lbl.setBackground(Color.red);
        lbl.setBounds(80, 80, 210, 30);
        lbl.setFont(Tools.getFont(12));
        this.add(lbl);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type) {
        return a -> {
            System.out.println("Listening: " + this.getClass().getSimpleName());
            
            // do nothing
            GraphBuilder builder = type.getGraphBuilder();
            super.simWindow.onNewGraphImport(builder);
        };
    }
    
    public static StaticTestGraphOptionPanel getInstance() {
        return instance;
    }
}
