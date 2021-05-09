package impl.windows;

import core.GraphBuilder;
import core.Window;
import impl.AlgorithmController;
import impl.MyGraph;
import impl.SimulationManager;
import impl.panels.*;
import impl.tools.Tools;

import javax.swing.*;
import java.awt.*;

public class SimulationWindow extends Window {
    
    AlgorithmController algoController;
    MainPanel mainPanel;
    
    MyGraph graph;
    
    final int menuWidth = 150;
    
    public SimulationWindow() {
        super(new Dimension(Tools.INITIAL_WINDOW_WIDTH, Tools.INITIAL_WINDOW_HEIGHT));
        this.frame.remove(this.panel);
        this.frame.setLayout(new BorderLayout());
        
        mainPanel = new MainPanel(this);
        this.frame.add(mainPanel, BorderLayout.CENTER);
        
        this.frame.pack();
    }
    
    // TODO
    //   many more things need to be done on graph change
    //   buttons, history!, listeners, ...
    //   algorithmController needs to be re-inited (workers need new nodes)
    public void onNewGraphImport(GraphBuilder builder) {
        this.graph = builder.buildGraph();
        this.graph.drawEdges(true);
        
        // re-links graph to all children
        mainPanel.setNewGraph(graph);
    
        algoController = new AlgorithmController(graph);
        algoController.addObserver(this.mainPanel.getTopPanel().getSimulationPanel());
        algoController.addObserver(this.mainPanel.getBottomPanel().getTabsPanel().getStateHistoryTab());
    
        Thread controller = new Thread(algoController);
        controller.start();
    }
    
    public SimulationPanel getSimulationPanel() { return this.mainPanel.getTopPanel().getSimulationPanel(); }
    
    public AlgorithmController getAlgorithmController() { return this.algoController; }
    
    public MainPanel getMainPanel() { return this.mainPanel; }
}
