import java.util.concurrent.atomic.*;

public class Counter {
    private int count = 0;                          // (A)
    public static void main(String... args) throws Exception {
        Counter obj = new Counter();
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                obj.count++;                        // (B)
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println(obj.count);
    }
}