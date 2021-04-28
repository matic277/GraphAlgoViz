package impl.tools;

public class LOG {
    
    public static void out(String premsg, String msg) {
        System.out.println(premsg + "["+Thread.currentThread().getName()+"]: " + msg);
    }
}
