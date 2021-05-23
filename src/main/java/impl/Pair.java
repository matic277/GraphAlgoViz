package impl;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Pair<A, B> {
    A a; B b;
    
    public Pair(A a, B b) { this.a = a; this.b = b; }
    
    public A getA() { return a; }
    public B getB() { return b; }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(a.toString())
                .append(b.toString())
                .toHashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if((o == null) || (o.getClass() != this.getClass())) {
            return false;
        }
        Pair p = (Pair) o;
        return a.toString().equals(p.a.toString()) &&
                b.toString().equals(p.b.toString());
    }
    
    @Override
    public String toString() {
        return "[A="+a.toString()+", B="+b.toString()+"]";
    }
}
