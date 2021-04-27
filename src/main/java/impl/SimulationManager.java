package impl;

import core.GraphBuilder;
import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.*;

public class SimulationManager {
    
    Graph graph;
    GraphBuilder builder;
    AlgorithmController algoController;
    
    SimulationWindow simWindow;
    
    public SimulationManager(GraphBuilder builder) {
        this.builder = builder;
        this.graph = new Graph();
        
        // open main window
        SwingUtilities.invokeLater(() -> {
            simWindow = new SimulationWindow(new Dimension(1000, 800), graph);
        });
        
        algoController = new AlgorithmController(graph.nodes);
        Thread controller = new Thread(algoController);
        controller.start();
    }
    
}
