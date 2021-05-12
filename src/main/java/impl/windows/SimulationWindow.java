package impl.windows;

import core.GraphBuilder;
import core.Window;
import impl.AlgorithmController;
import impl.MyGraph;
import impl.panels.*;
import impl.tools.Tools;

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
        
        this.graph = MyGraph.getInstance();
        
        this.mainPanel = new MainPanel(this);
        this.frame.add(mainPanel, BorderLayout.CENTER);
        
        this.algoController = new AlgorithmController(MyGraph.getInstance());
//        this.algoController.addObserver(this.mainPanel.getTopPanel().getSimulationPanel());
        this.algoController.addObserver(this.mainPanel.getBottomPanel().getTabsPanel().getStateHistoryTab());
    
        this.frame.pack();
    }
    
    // TODO
    //   many more things need to be done on graph change
    //   buttons, history!, listeners, ...
    //   algorithmController needs to be re-inited (workers need new nodes)
    public void onNewGraphImport(GraphBuilder builder) {
        AlgorithmController.totalStates = 1;
        AlgorithmController.currentStateIndex = 0;
        
        this.graph.clearGraph();
        builder.buildGraph();
        this.graph.drawEdges(true);
        
        // TODO: unnecessary if graph is singleton
        // re-links graph to all children
        mainPanel.onNewGraphImport();
        
        
        algoController.setNewGraph(graph);
        
        Thread controller = new Thread(algoController);
        controller.start();
    }
    
    public SimulationPanel getSimulationPanel() { return this.mainPanel.getTopPanel().getSimulationPanel(); }
    
    public AlgorithmController getAlgorithmController() { return this.algoController; }
    
    public MainPanel getMainPanel() { return this.mainPanel; }
}
