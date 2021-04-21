package impl;

import core.Algorithm;
import impl.tools.Tools;

import java.util.Collection;
import java.util.Random;

public class AlgorithmController implements Runnable {
    
    Collection<Node> nodes;
    
    Algorithm algo;
    
    public AlgorithmController(Collection<Node> nodes) {
        this.nodes = nodes;
        this.algo = getAlgorithm();
        // master commit
    }
    
    @Override
    public void run() {
        while (true) {
            System.out.println("--- ROUND START ---");
            for (Node n : nodes) {
                Algorithm a = getAlgorithm();
//                System.out.println("running");
                new Thread(() -> {
                    a.run(n);
                }).start();
//                System.out.println("done");
            }
            System.out.println("--- ROUND DONE ---");
            Tools.sleep(1000);
        }
    }
    
    public Algorithm getAlgorithm() {
        Random r = new Random();
        return node -> {
            if (node.info == 0) {
                Communicator.receiveMessage(node);
                if (node.msgs != null) {
                    System.out.println("("+node.id+") receiving: " + node.msgs.getFirst().msg);
                    node.info += node.msgs.getFirst().msg;
                } else {
                    System.out.println("("+node.id+") receiving: null");
                }
            } else {
                Node receiver = node.neighbors.get(r.nextInt(node.neighbors.size()));
                System.out.println("("+node.id+") sending to ("+receiver.id +")");
                Communicator.sendMessage(
                        receiver,
                        new Message(node.info));
            }
        };
    }
    
    
    
    public void setAlgorithm(Algorithm a) { algo = a; }
}
