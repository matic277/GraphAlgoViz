package impl.panels.importPanels.graphOptionPanels;

import impl.graphBuilders.GraphBuilder;
import core.GraphType;
import impl.tools.Tools;
import impl.windows.ImportGraphWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StaticTestGraphOptionPanel extends OptionPanel {
    
    private static final StaticTestGraphOptionPanel instance = new StaticTestGraphOptionPanel(null);
    
    private StaticTestGraphOptionPanel(ImportGraphWindow parent) {
        super(parent);
    
        JLabel lbl = new JLabel("DEBUG: Load predefined graph");
        this.add(lbl);
    }
    
    @Override
    public ActionListener getButtonAction(GraphType type, JFrame importWindow) {
        return a -> {
            System.out.println("Listening: " + this.getClass().getSimpleName());
    
            // Close window
            importWindow.setVisible(false);
            importWindow.dispose();
            
            // do nothing
            GraphBuilder builder = type.getGraphBuilder();
            super.simWindow.onNewGraphImport(builder);
        };
    }
    
    public static StaticTestGraphOptionPanel getInstance() {
        return instance;
    }
}
