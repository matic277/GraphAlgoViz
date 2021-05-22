package impl.windows;

import impl.panels.importPanels.ImportGraphPanel;

import java.awt.*;

import static javax.swing.UIManager.getFont;

public class ImportGraphWindow extends Window {
    
    SimulationWindow parent;
    ImportGraphPanel panel;
    
    
    
    
    
    public ImportGraphWindow(SimulationWindow parent) {
        super(new Dimension(500, 550));
        this.parent = parent;
        
        this.panel = new ImportGraphPanel(this);
        
        this.addMainComponent(panel);
    }
    
    public SimulationWindow getSimulationWindow() { return this.parent; }
    
    public Frame getFrame() { return this.frame; }

    
}
