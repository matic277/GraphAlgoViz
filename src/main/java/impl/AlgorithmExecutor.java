package impl;

import core.Algorithm;
import impl.tools.LOG;

import java.util.Set;
import java.util.concurrent.BrokenBarrierException;

public class AlgorithmExecutor implements Runnable {
    
    Set<Node> nodes;
    Algorithm algorithm;
    
    String name;
    
    // this is a workaround to the problem of naming threads
    // in ExecutorService
    // renames thread once, then does nothing
    Runnable threadNamer = () -> { Thread.currentThread().setName(name); threadNamer = () -> { };};
    
    public AlgorithmExecutor(Set<Node> nodes, Algorithm algorithm, String threadName) {
        this.nodes = nodes;
        this.algorithm = algorithm;
        this.name = threadName;
        System.out.println("new executor named: " + threadName + " tasks=" + nodes);
    }
    
    @Override
    public void run() {
        threadNamer.run();
//        System.out.println("Thread '"+name+"' stared.");
        
        nodes.forEach(n -> {
//            LOG.out("  ->", "Algo starting on node " + n + ".");
            
            State newState = algorithm.run(n);
            n.addState(newState);
            
            if (newState.getState() >= 0 &&
                n.states.get(AlgorithmController.currentStateIndex).getState() == 0) {
                MyGraph.getInstance().signalNewInformedNode();
            }
            
//            LOG.out("  ->", "Algo done on node     " + n + ".");
        });
        
        LOG.out("  ->", "AlgoExecutor done for all nodes, waiting on barrier.");
        
        try { AlgorithmController.BARRIER.await(); }
        catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
        
//        LOG.out("  ->", "Barrier tipped, thread exiting.");
    }
    
    public String stateToString() {
        return name + " -> " + nodes.size();
    }
}
