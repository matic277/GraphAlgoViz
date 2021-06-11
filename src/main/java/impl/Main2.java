package impl;

import core.Algorithm;
import impl.tools.Tools;
import impl.windows.GraphAlgoVis;

public class Main2 {
    
    // ACTUAL USAGE
    public static void main(String[] args) {
        Algorithm myAlgo = getAlgorithm();
        GraphAlgoVis gav = GraphAlgoVis.get(myAlgo);
        gav.openWindow();
    }
    
    public static Algorithm getAlgorithm() {
        return node -> {
            // if you have info, don't do anything
            if (node.getState().info > 0) return new State(node.getState().info);
            
            // Some nodes have no neighbors, so
            // in this case don't do anything.
            // Return the same state you're in.
            if (node.getNeighbors().isEmpty()) return new State(node.getState().info);
            
            // get two random neighbors
            Node randNeigh1 = node.getNeighbors().get(Tools.RAND.nextInt(node.getNeighbors().size()));
            Node randNeigh2 = node.getNeighbors().get(Tools.RAND.nextInt(node.getNeighbors().size()));
            State stateOfNeigh1 = randNeigh1.getState();
            State stateOfNeigh2 = randNeigh2.getState();
            
            // or
            int newStateInfo = stateOfNeigh1.info | stateOfNeigh2.info | node.getState().info;
            
            return new State(newStateInfo);
        };
    }
}
