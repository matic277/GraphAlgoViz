package impl;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CyclicBarrier;

public class Communicator<G> {
    
    static final Map<Node, Deque<Message>> messages = new ConcurrentHashMap<>();
    static final CyclicBarrier barrier = new CyclicBarrier(Graph.numOfNodes); // transfer this to env
    
    public Communicator(int numOfNodes) {
    
    }
    
    public static void sendMessage(Node receiver, Message msg) {
        messages.computeIfAbsent(receiver, key -> new ConcurrentLinkedDeque<>())
                .add(msg);
        System.out.println("  waiting in send");
        try { barrier.await(); }
        catch (Exception e) { e.printStackTrace(); }
//        System.out.println("  done in send");
    }
    
    public static void receiveMessage(Node receiver) {
        System.out.println("1waitig in receive");
        try { barrier.await(); }
        catch (Exception e) { e.printStackTrace(); }
        
//        System.out.println("1done in receive");
        receiver.receiveMessage(messages.get(receiver));
    
//        System.out.println("2waitig in receive");
        try { barrier.await(); }
        catch (Exception e) { e.printStackTrace(); }
//        System.out.println("2done in receive");
    }
}
