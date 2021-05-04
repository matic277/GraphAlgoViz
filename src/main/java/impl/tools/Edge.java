package impl.tools;

import impl.Node;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class Edge {
    
    Node n1, n2;
    
    public Edge(Node n1, Node n2) {
        this.n1 = n1; this.n2 = n2;
    }
    
    public Node getN1() { return n1; }
    public Node getN2() { return n2; }
    
    public static String edgesListToString(List<Node> list) {
        StringBuilder sb = new StringBuilder()
                .append("[");
        if (list.isEmpty()) return sb.append("]").toString();
        
        for (int i=0; i<list.size()-1; i++) {
            sb.append(list.get(i).id).append(", ");
        }
        sb.append(list.get(list.size()-1).id);
        return sb.append("]").toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if((o == null) || (o.getClass() != this.getClass())) {
            return false;
        }
        Edge e = (Edge) o;
        
        return (n1.id == e.n1.id || n1.id == e.n2.id) &&
               (n2.id == e.n1.id || n2.id == e.n2.id);
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(Math.max(n1.id, n2.id))
                .append(Math.min(n1.id, n2.id))
                .toHashCode();
    }
}
