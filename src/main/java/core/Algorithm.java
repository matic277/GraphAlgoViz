package core;

import impl.Node;
import impl.State;

import java.util.List;

@FunctionalInterface
public interface Algorithm {
    // TODO add pausing options?
    public State run(Node node);
}
