package impl;

import java.util.List;

public class State {
    
    int info;
    
    public State(int info) {
        this.info = info;
    }
    
    public State() {
        this(0);
    }
    
    public int getState() {
        return info;
    }
    
    public void setState(int info) {
        this.info = info;
    }
    
    // for debug component drawer, mark current state State with "|"
    public static String stateListToString(List<State> list) {
        StringBuilder sb = new StringBuilder()
                .append("[");
        
        // this could be optimized by spliting for loops into two
        // one from 0...curretState
        // append |currstate|
        // continue from currState...list.len
        for (int i=0; i<list.size()-1; i++) {
            int state = list.get(i).getState();
            sb.append(AlgorithmController.currentStateIndex == i ? "|"+state+"|, " : state+", ");
        }
        int state = list.get(list.size()-1).getState();
        sb.append(AlgorithmController.currentStateIndex == list.size()-1 ? "|"+state+"|" : state);
        return sb.append("]").toString();
    }
    
    @Override
    public String toString() {
        return info + "";
    }
}
