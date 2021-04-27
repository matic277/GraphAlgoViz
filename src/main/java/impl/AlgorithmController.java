package impl;

import core.Algorithm;
import impl.tools.Tools;

import java.util.*;
import java.util.concurrent.*;

public class AlgorithmController implements Runnable {
    
    static final int PROCESSORS = 3;
    
    // TODO: update on change of nodes
    static final CyclicBarrier BARRIER = new CyclicBarrier(PROCESSORS + 1);
    static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(PROCESSORS);
    static final AlgorithmExecutor[] EXECUTORS = new AlgorithmExecutor[PROCESSORS];
    
    int currentStateIndex = 0;
    int totalStates = 0;
    
    Collection<Node> nodes;
    Algorithm algo;
    
    public static volatile boolean PAUSE = true;
    public static final Object PAUSE_LOCK = new Object();
    
    public AlgorithmController(Collection<Node> nodes) {
        this.nodes = nodes;
        this.algo = getAlgorithm();
        
        assignTasks();
    }
    
    
    @Override
    public void run() {
        Thread.currentThread().setName("CONTROLLER");
        while (true)
        {
            AlgorithmExecutor.LOG("\n->", "STARTING EXECUTORS.");
            for (int i=0; i<EXECUTORS.length; i++) {
                THREAD_POOL.submit(EXECUTORS[i]);
            }
            AlgorithmExecutor.LOG("->", "ALL EXECUTORS STARTED.");
            
            try { AlgorithmController.BARRIER.await(); }
            catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
            
            AlgorithmExecutor.LOG("\n->", "BARRIER TIPPED.");
            
            if (PAUSE) {
                AlgorithmExecutor.LOG("->", "PAUSING.");
                
                synchronized (PAUSE_LOCK) {
                    while (PAUSE) {
                        try { PAUSE_LOCK.wait(); }
                        catch (Exception e) { e.printStackTrace(); }
                    }
                }
                AlgorithmExecutor.LOG("->", "CONTINUING.");
            }
            
            
            Tools.sleep(2000);
        }
    }
    
    private void assignTasks() {
        int nodes = this.nodes.size();
        int taskSize = nodes / PROCESSORS;
        
        int lastTaskSize = (nodes - (taskSize * PROCESSORS)) + taskSize;
    
        System.out.println("TASK SIZE="+taskSize+", LAST="+lastTaskSize);
    
        Iterator<Node> iter = this.nodes.stream().iterator();
        
        for (int i=0; i<EXECUTORS.length; i++) {
            // last processor might do more work (task divisibility problem)
            int nodeCounter = i == EXECUTORS.length-1 ? lastTaskSize : taskSize;
            List<Node> nodesToProcess = new ArrayList<>((int)(taskSize*1.1));
            
            while(iter.hasNext() && --nodeCounter >= 0) {
                nodesToProcess.add(iter.next());
            }
            
            EXECUTORS[i] = new AlgorithmExecutor(nodesToProcess, getAlgorithm(), "PR-"+i);
        }
        
    }
    
    public Algorithm getAlgorithm() {
        Random r = new Random();
        return node -> {
            // if you have info, don't do anything
            if (node.getState().info > 0) return new State(node.getState().info);
            
            // get two random neighbors
            State stateOfNeigh1 = node.neighbors.get(r.nextInt(node.neighbors.size())).getState();
            State stateOfNeigh2 = node.neighbors.get(r.nextInt(node.neighbors.size())).getState();
            
            // or
            int newStateInfo = stateOfNeigh1.info | stateOfNeigh2.info | node.getState().info;
            
            return new State(newStateInfo);
        };
    }
    
    
    public void setAlgorithm(Algorithm a) { algo = a; }
}
