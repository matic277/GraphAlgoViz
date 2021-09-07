import core.Algorithm;
import impl.Node;
import impl.State;
import impl.Vertex;
import impl.tools.Tools;

public class AlgorithmImpl implements Algorithm {
    @Override
    public State run(Vertex vertex) {
        // if you have info, don't do anything
        if (vertex.getState().info > 0) return new State(vertex.getState().info);
        
        // Some nodes have no neighbors, so
        // in this case don't do anything.
        // Return the same state you're in.
        if (vertex.getNeighbors().isEmpty()) return new State(vertex.getState().info);
        
        // get two random neighbors
        Node randNeigh1 = vertex.getNeighbors().get(Tools.RAND.nextInt(vertex.getNeighbors().size()));
        Node randNeigh2 = vertex.getNeighbors().get(Tools.RAND.nextInt(vertex.getNeighbors().size()));
        State stateOfNeigh1 = randNeigh1.getState();
        State stateOfNeigh2 = randNeigh2.getState();
        
        // or
        int newStateInfo = stateOfNeigh1.info | stateOfNeigh2.info | vertex.getState().info;
        
        return new State(newStateInfo);
    }
}
