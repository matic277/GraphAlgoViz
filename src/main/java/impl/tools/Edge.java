package impl.tools;

import impl.Node;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Edge {
    
    // drawing opacity
    public static int opacity = 255;
    
    Node n1, n2;
    
    public Edge(Node n1, Node n2) {
        this.n1 = n1; this.n2 = n2;
    }
    
    public Node getN1() { return n1; }
    public Node getN2() { return n2; }
    
    public static String edgesListToString(Collection<Node> col) {
        StringBuilder sb = new StringBuilder()
                .append("[");
        if (col.isEmpty()) return sb.append("]").toString();
    
        Iterator<Node> iter = col.iterator();
        for (; iter.hasNext();) {
            Node n = iter.next();
            sb.append(n.id).append(iter.hasNext() ? ", " : "]");
        }
//        sb.append(list.get(list.size()-1).id);
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
