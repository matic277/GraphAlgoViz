package impl;

import core.Algorithm;

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
        System.out.println("new executor named: " + threadName);
    }
    
    @Override
    public void run() {
        Thread.currentThread().setName(name);
        System.out.println("Thread '"+name+"' stared.");
        
        nodes.forEach(n -> {
            LOG("  ->", "Algo starting on node " + n + ".");
            State newState = algorithm.run(n);
            n.addState(newState);
            LOG("  ->", "Algo done on node     " + n + ".");
        });
        
        LOG("  ->", "AlgoExecutor done for all nodes, waiting on barrier.");
        
        try { AlgorithmController.BARRIER.await(); }
        catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
        
        LOG("  ->", "Barrier tipped, thread exiting.");
    }
    
    public static void LOG(String premsg, String msg) {
        System.out.println(premsg + "["+Thread.currentThread().getName()+"]: " + msg);
    }
}
