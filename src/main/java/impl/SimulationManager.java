package impl;

import core.GraphBuilder;
import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.*;

public class SimulationManager {
    
    MyGraph graph;
    GraphBuilder builder;
    AlgorithmController algoController;
    
    SimulationWindow simWindow;
    
    public SimulationManager(GraphBuilder builder) {
        this.builder = builder;
        this.graph = builder.buildGraph();
        this.graph.drawEdges(true);
        
        algoController = new AlgorithmController(graph);
        Thread controller = new Thread(algoController);
        
        // open main window
        SwingUtilities.invokeLater(() -> {
            Dimension simSize = new Dimension(1400, 1000);
            simWindow = new SimulationWindow(simSize, graph, this);
            
            // TODO:
//            algoController.addObserver(simWindow.getSimulationPanel());
//            algoController.addObserver(simWindow.getSimulationPanel().getStateInfoSubmenu());
        });
        
        controller.start();
    }
    
    public AlgorithmController getAlgorithmController() {
        return this.algoController;
    }
    
    public MyGraph getGraph() {
        return this.graph;
    }
    
}
