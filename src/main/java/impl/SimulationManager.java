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
        
        // open main window
        SwingUtilities.invokeLater(() -> {
            simWindow = new SimulationWindow(new Dimension(1000, 800), graph);
        });
        
        algoController = new AlgorithmController(graph);
        Thread controller = new Thread(algoController);
        controller.start();
    }
    
}
