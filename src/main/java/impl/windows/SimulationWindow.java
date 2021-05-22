package impl.windows;

import impl.graphBuilders.GraphBuilder;
import impl.AlgorithmController;
import impl.MyGraph;
import impl.panels.simulationPanels.MainPanel;
import impl.panels.simulationPanels.SimulationPanel;
import impl.tools.Tools;

import java.awt.*;

public class SimulationWindow extends Window {
    
    AlgorithmController algoController;
    MainPanel mainPanel;
    
    MyGraph graph;
    
    final int menuWidth = 150;
    
    public SimulationWindow() {
        super(new Dimension(Tools.INITIAL_WINDOW_WIDTH, Tools.INITIAL_WINDOW_HEIGHT));
        
        this.graph = MyGraph.getInstance();
        this.mainPanel = new MainPanel(this);
        this.addMainComponent(mainPanel);
        
        this.algoController = new AlgorithmController(MyGraph.getInstance());
//        this.algoController.addObserver(this.mainPanel.getTopPanel().getSimulationPanel());
        this.algoController.addObserver(this.mainPanel.getBottomPanel().getTabsPanel().getStateHistoryTab());
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
        
        // re-enables buttons and such
        mainPanel.onNewGraphImport();
        
        this.graph.setNumberOfInformedNodes(builder.getNumberOfInitiallyInformedNodes());
        this.graph.onInformedNodesChange();
        
        algoController.setNewGraph(graph);
        
        Thread controller = new Thread(algoController);
        controller.start();
    }
    
    public SimulationPanel getSimulationPanel() { return this.mainPanel.getTopPanel().getSimulationPanel(); }
    
    public AlgorithmController getAlgorithmController() { return this.algoController; }
    
    public MainPanel getMainPanel() { return this.mainPanel; }
}
