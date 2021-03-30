package impl.tools;

public class Pair<T> {
    
    T a, b;
    
    public Pair(T a, T b) {
        this.a = a; this.b = b;
    }
    
    public T getA() { return a; }
    public T getB() { return b; }
}
