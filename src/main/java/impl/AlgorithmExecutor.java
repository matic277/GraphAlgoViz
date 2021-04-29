package impl;

import core.Algorithm;
import impl.tools.LOG;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public class AlgorithmExecutor implements Runnable {
    
    List<Node> nodes;
    Algorithm algorithm;
    
    String name;
    
    public AlgorithmExecutor(List<Node> nodes, Algorithm algorithm, String threadName) {
        this.nodes = nodes;
        this.algorithm = algorithm;
        this.name = threadName;
        nodes.sort(Comparator.comparing(Node::getId));
        System.out.println("new executor named: " + threadName + " tasks=" + nodes);
    }
    
    @Override
    public void run() {
        Thread.currentThread().setName(name);
        System.out.println("Thread '"+name+"' stared.");
        
        nodes.forEach(n -> {
            LOG.out("  ->", "Algo starting on node " + n + ".");
            
            State newState = algorithm.run(n);
            n.addState(newState);
            
            LOG.out("  ->", "Algo done on node     " + n + ".");
        });
        
        LOG.out("  ->", "AlgoExecutor done for all nodes, waiting on barrier.");
        
        try { AlgorithmController.BARRIER.await(); }
        catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
    
        LOG.out("  ->", "Barrier tipped, thread exiting.");
    }
}
