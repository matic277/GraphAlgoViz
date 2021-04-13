package core;

import impl.Communicator;
import impl.Node;

import java.util.List;

@FunctionalInterface
public interface Algorithm {
    // TODO add pausing options?
    public void run(Node node);
}
