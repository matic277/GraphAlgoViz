package core;

import impl.Node;
import impl.State;

import java.util.List;

/**
 * An algorithm accepts the node in which it's being executed.
 * Returns new state of accepted node.
 */
@FunctionalInterface
public interface Algorithm {
    public State run(Node node);
}
