package impl.windows;

import core.Algorithm;
import impl.graphBuilders.GraphBuilder;
import impl.AlgorithmController;
import impl.MyGraph;
import impl.panels.simulationPanels.MainPanel;
import impl.panels.simulationPanels.SimulationPanel;
import impl.tools.Tools;

import javax.print.attribute.standard.DocumentName;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.concurrent.CompletableFuture;

public class SimulationWindow extends Window {
    
    Thread controllerThread;
    AlgorithmController algoController;
    MainPanel mainPanel;
    
    MyGraph graph;
    
    final int menuWidth = 150;
    
    public SimulationWindow(Algorithm algo) {
        super(new Dimension(Tools.INITIAL_WINDOW_WIDTH, Tools.INITIAL_WINDOW_HEIGHT));
        
        this.graph = MyGraph.getInstance();
        this.mainPanel = new MainPanel(this);
        this.addMainComponent(mainPanel);
        
        this.algoController = new AlgorithmController(MyGraph.getInstance(), algo);
//        this.algoController.addObserver(this.mainPanel.getTopPanel().getSimulationPanel());
        this.algoController.addObserver(this.mainPanel.getBottomPanel().getTabsPanel().getStateHistoryTab());
        
        this.controllerThread = new Thread(algoController);
    }
    
    // TODO
    //   many more things need to be done on graph change
    //   buttons, history!, listeners, ...
    //   algorithmController needs to be re-inited (workers need new nodes)
    // BUILDER IS NULLABLE!!!
    public void onNewGraphImport(GraphBuilder builder) {
        // Completable future,
        // on large graph imports, this makes sure GUI doesn't freeze
        // TODO: disabling buttons
        CompletableFuture.runAsync(() ->{
            AlgorithmController.totalStates = 1;
            AlgorithmController.currentStateIndex = 0;
            
            // when this is called with null
            // one node was added by hand
            // so there is nothing to clear and/or build
            if (builder != null) {
                this.graph.clearGraph();
                builder.buildGraph();
                this.graph.setNumberOfInformedNodes(builder.getNumberOfInitiallyInformedNodes());
            }
            
            // re-enables buttons and such
            this.graph.onGraphImport();
        });
        
        // This stops working if inside completableFuture
        // stopped working when side menu because top menu
        // with icons instead of text buttons.
        // ???
        if (!controllerThread.isAlive()) {
            System.out.println("here");
            controllerThread.start();
        }
    }
    
    public SimulationPanel getSimulationPanel() { return this.mainPanel.getTopPanel().getSimulationPanel(); }
    
    public AlgorithmController getAlgorithmController() { return this.algoController; }
    
    public MainPanel getMainPanel() { return this.mainPanel; }
    
    public Thread getControllerThread() { return  this.controllerThread; }
}
