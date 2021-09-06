package impl;

import core.Algorithm;
import core.GraphChangeObserver;
import core.StateObservable;
import core.StateObserver;
import impl.panels.simulationPanels.MenuPanel;
import impl.tools.LOG;
import impl.tools.Tools;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class AlgorithmController implements Runnable, StateObservable, GraphChangeObserver {
    
    static final int PROCESSORS = 3;
    
    // TODO: update on change of nodes
    static final CyclicBarrier BARRIER = new CyclicBarrier(PROCESSORS + 1);
    final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(PROCESSORS);
    final AlgorithmExecutor[] EXECUTORS = new AlgorithmExecutor[PROCESSORS];
    public static final AtomicBoolean NEXT_ROUND_BUTTON_PRESSED = new AtomicBoolean(false);
    
    public static int currentStateIndex = 0; // atomic?
    public static int totalStates = 1;
    
    MyGraph graph;
    final Algorithm algo;
    
    public static volatile AtomicBoolean PAUSE = new AtomicBoolean(true);
    public static final Object PAUSE_LOCK = new Object();
    public static int TIMEOUT_BETWEEN_ROUNDS = 100;
    
    public AlgorithmController(MyGraph graph, Algorithm algo) {
        this.graph = graph;
        this.algo = algo;
        
        MyGraph.getInstance().addObserver(this);
        
        initProcessors();
//        assignTasks();
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
            
            MenuPanel.nextBtn.setEnabled(AlgorithmController.PAUSE.get());
            MenuPanel.prevBtn.setEnabled(AlgorithmController.PAUSE.get());
            
            
            Tools.sleep(TIMEOUT_BETWEEN_ROUNDS);
        }
    }
    
    private void incrementState() {
        totalStates++;
        currentStateIndex++;
        observers.forEach(StateObserver::onStateChange);
    }
    
    private void initProcessors() {
//        ThreadFactory factor;
//        new ExecutorService();
//
        for (int i=0; i<PROCESSORS; i++) {
            EXECUTORS[i] = new AlgorithmExecutor(new HashSet<>(), algo, "PR-"+i);
        }
    }
    
    public void assignTasks() {
        // Clear all tasks(nodes) from all processors first
        for (int i=0; i<EXECUTORS.length; i++) EXECUTORS[i].nodes.clear();
        
        // There are no tasks(nodes) to assign
        if (graph.getGraph().vertexSet().isEmpty()) {
            return;
        }
        
        int nodes = this.graph.getNodes().size();
        int taskSize = nodes / PROCESSORS;
        int lastTaskSize = (nodes - (taskSize * PROCESSORS)) + taskSize;
        
        Iterator<Node> iter = this.graph.getNodes().stream().iterator();
        
        for (int i=0; i<PROCESSORS; i++) {
            // last processor might do more work (task divisibility problem)
            int nodeCounter = i == PROCESSORS-1 ? lastTaskSize : taskSize;
            Set<Node> nodesToProcess = new HashSet<>((int)(taskSize*1.1));
            
            while(iter.hasNext() && --nodeCounter >= 0) {
                nodesToProcess.add(iter.next());
            }
            EXECUTORS[i].nodes.addAll(nodesToProcess);
        }
        
        System.out.println();
        System.out.println("Assigned tasks:");
        for (AlgorithmExecutor ex : EXECUTORS) {
            System.out.println(" -> " + ex.stateToString());
        }
    }
    
    
    //public void removeNode(Node node) {
    //    for (AlgorithmExecutor ex : EXECUTORS) {
    //        boolean foundAndRemoved = ex.nodes.remove(node);
    //        if (foundAndRemoved) {
    //            return;
    //        }
    //    }
    //    throw new RuntimeException("Node " + node + " not found and removed!");
    //}
    
    //public Algorithm getAlgorithm() {
    //    return node -> {
    //        // if you have info, don't do anything
    //        if (node.getState().info > 0) return new State(node.getState().info);
    //
    //        // Some nodes have no neighbors, so
    //        // in this case don't do anything.
    //        // Return the same state you're in.
    //        if (node.getNeighbors().isEmpty()) return new State(node.getState().info);
    //
    //        // get two random neighbors
    //        Node randNeigh1 = node.getNeighbors().get(Tools.RAND.nextInt(node.getNeighbors().size()));
    //        Node randNeigh2 = node.getNeighbors().get(Tools.RAND.nextInt(node.getNeighbors().size()));
    //        State stateOfNeigh1 = randNeigh1.getState();
    //        State stateOfNeigh2 = randNeigh2.getState();
    //
    //        // or
    //        int newStateInfo = stateOfNeigh1.info | stateOfNeigh2.info | node.getState().info;
    //
    //        return new State(newStateInfo);
    //    };
    //}
    
    //public void setAlgorithm(Algorithm a) { algo = a; }
    
    Set<StateObserver> observers = new HashSet<>(8);
    
    @Override
    public void addObserver(StateObserver obsever) {
        this.observers.add(obsever);
    }
    
    @Override
    public void removeObserver(StateObserver observer) {
        this.observers.remove(observer);
    }
    
    @Override
    public void onGraphClear() {
        AlgorithmController.totalStates = 1;
        AlgorithmController.currentStateIndex = 0;
        
        assignTasks();
    }
    // TODO methods have the same body
    @Override
    public void onGraphImport() {
        AlgorithmController.totalStates = 1;
        AlgorithmController.currentStateIndex = 0;
        
        assignTasks();
    }
    
    @Override
    public void vertexAdded(GraphVertexChangeEvent<Node> e) {
        // add new node to some random processor
        int randomProc = Tools.RAND.nextInt(PROCESSORS);
        EXECUTORS[randomProc].addNewNodeToProcess(e.getVertex());
    
        AlgorithmController.totalStates = AlgorithmController.currentStateIndex + 1;
    }
    
    @Override
    public void vertexRemoved(GraphVertexChangeEvent<Node> e) {
        AlgorithmController.totalStates = AlgorithmController.currentStateIndex + 1;
        
        // one of the executors is processing a node that has been removed
        Node nodeToRemove = e.getVertex();
        for (AlgorithmExecutor ex : EXECUTORS) {
            boolean foundAndRemoved = ex.nodes.remove(nodeToRemove);
            if (foundAndRemoved) {
                return;
            }
        }
        throw new RuntimeException("Node " + nodeToRemove + " not found and wasn't removed from any executor!");
    }
    
    @Override public void onNewInformedNode() {}
    @Override public void onNewUninformedNode() {}
    @Override public void edgeAdded(GraphEdgeChangeEvent<Node, DefaultEdge> e) {}
    @Override public void edgeRemoved(GraphEdgeChangeEvent<Node, DefaultEdge> e) {}
}
