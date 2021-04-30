package impl;

import core.Algorithm;
import core.Observable;
import core.Observer;
import impl.panels.MenuPanel;
import impl.tools.LOG;
import impl.tools.Tools;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class AlgorithmController implements Runnable, Observable {
    
    static final int PROCESSORS = 3;
    
    // TODO: update on change of nodes
    static final CyclicBarrier BARRIER = new CyclicBarrier(PROCESSORS + 1);
    final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(PROCESSORS);
    final AlgorithmExecutor[] EXECUTORS = new AlgorithmExecutor[PROCESSORS];
    public static final AtomicBoolean NEXT_ROUND_BUTTON_PRESSED = new AtomicBoolean(false);
    
    int currentStateIndex = 0;
    int totalStates = 0;
    
    MyGraph graph;
    Algorithm algo;
    
    public static volatile AtomicBoolean PAUSE = new AtomicBoolean(true);
    public static final Object PAUSE_LOCK = new Object();
    
    public AlgorithmController(MyGraph graph) {
        this.graph = graph;
        this.algo = getAlgorithm();
        
        assignTasks();
    }
    
    @Override
    public void run() {
        Thread.currentThread().setName("CONTROLLER");
        while (true)
        {
            if (PAUSE.getAcquire()) {
                LOG.out("->", "PAUSING.");
                synchronized (PAUSE_LOCK) {
                    while (PAUSE.getAcquire() && !NEXT_ROUND_BUTTON_PRESSED.get()) {
                        try { PAUSE_LOCK.wait(); }
                        catch (Exception e) { e.printStackTrace(); }
                    }
                    // woken up
                    // if woken up by button, the set the button press to false
                    // if not woken up by button, then don't do anything and just continue
                    boolean wasPressed = NEXT_ROUND_BUTTON_PRESSED.compareAndSet(true, false);
                }
                LOG.out("->", "CONTINUING.");
            }
            
            LOG.out("\n->", "STARTING EXECUTORS.");
            for (int i=0; i<EXECUTORS.length; i++) {
                THREAD_POOL.submit(EXECUTORS[i]);
            }
            LOG.out("->", "ALL EXECUTORS STARTED.");
            
            try { AlgorithmController.BARRIER.await(); }
            catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
            
            incrementState();
            
            LOG.out("\n->", "BARRIER TIPPED.");
            LOG.out(" ->", "currentStateIndex="+currentStateIndex);
            LOG.out(" ->", "totalStates="+totalStates);
            
            // TODO thread safety
            // number of nodes can change?
            this.graph.getNodes().forEach(Node::incrementToNextState);
            MenuPanel.nextBtn.setEnabled(AlgorithmController.PAUSE.get());
            MenuPanel.prevBtn.setEnabled(AlgorithmController.PAUSE.get());
            
            
            Tools.sleep(1000);
        }
    }
    
    private void incrementState() {
        currentStateIndex++;
        totalStates++;
        observers.forEach(obs -> obs.notifyStateChange(currentStateIndex));
    }
    
    private void assignTasks() {
        int nodes = this.graph.getNodes().size();
        int taskSize = nodes / PROCESSORS;
        int lastTaskSize = (nodes - (taskSize * PROCESSORS)) + taskSize;
        
        System.out.println("TASK SIZE="+taskSize+", LAST="+lastTaskSize);
        
        Iterator<Node> iter = this.graph.getNodes().stream().iterator();
        
        if (EXECUTORS.length != PROCESSORS) {
            throw new AssertionError("Number of executors expected to be " + PROCESSORS +
                                     ", but was: " + EXECUTORS.length + " instead.");
        }
        
        for (int i=0; i<PROCESSORS; i++) {
            // last processor might do more work (task divisibility problem)
            int nodeCounter = i == PROCESSORS-1 ? lastTaskSize : taskSize;
            List<Node> nodesToProcess = new ArrayList<>((int)(taskSize*1.1));
            
            while(iter.hasNext() && --nodeCounter >= 0) {
                nodesToProcess.add(iter.next());
            }
            EXECUTORS[i] = new AlgorithmExecutor(nodesToProcess, getAlgorithm(), "PR-"+i);
        }
    }
    
    public Algorithm getAlgorithm() {
        return node -> {
            // if you have info, don't do anything
            if (node.getState().info > 0) return new State(node.getState().info);
            
            // TODO
            // Some nodes have no neighbors, so
            // in this case don't do anything.
            // Return the same state you're in.
            if (node.neighbors.isEmpty()) return new State(node.getState().info);
            
            // get two random neighbors
            State stateOfNeigh1 = node.neighbors.get(Tools.RAND.nextInt(node.neighbors.size())).getState();
            State stateOfNeigh2 = node.neighbors.get(Tools.RAND.nextInt(node.neighbors.size())).getState();
            
            // or
            int newStateInfo = stateOfNeigh1.info | stateOfNeigh2.info | node.getState().info;
            
            return new State(newStateInfo);
        };
    }
    
    public void setAlgorithm(Algorithm a) { algo = a; }
    
    
    Set<Observer> observers = new HashSet<>(8);
    
    @Override
    public void addObserver(Observer obsever) {
        this.observers.add(obsever);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }
}
