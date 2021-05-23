package impl.windows;

import impl.panels.importPanels.ImportGraphPanel;

import javax.swing.*;
import java.awt.*;

public class ImportGraphWindow extends Window {
    
    SimulationWindow parent;
    ImportGraphPanel panel;
    
    public ImportGraphWindow(SimulationWindow parent) {
        super(new Dimension(500, 550));
        this.parent = parent;
        
        this.panel = new ImportGraphPanel(this);
        
        this.addMainComponent(panel);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    // DEBUG CONSTRUCTOR
    public ImportGraphWindow() {
        super(new Dimension(500, 550));
        this.panel = new ImportGraphPanel(this);
        this.addMainComponent(panel);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public SimulationWindow getSimulationWindow() { return this.parent; }
    
    public Frame getFrame() { return this.frame; }
}
