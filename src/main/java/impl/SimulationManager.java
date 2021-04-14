package impl;

import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.*;

public class SimulationManager {
    
    Graph graph;
    AlgorithmController algoController;
    
    SimulationWindow simWindow;
    
    public SimulationManager() {
        this.graph = new Graph();
        
        // open main window
        SwingUtilities.invokeLater(() -> {
            simWindow = new SimulationWindow(new Dimension(1000, 800), graph);
        });
        
//        algoController = new AlgorithmController(graph.nodes);
//        Thread controller = new Thread(algoController);
//        controller.start();
    }
    
}
