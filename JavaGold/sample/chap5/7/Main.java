import java.util.concurrent.atomic.AtomicInteger;

class Counter {
    private Integer value = 0;
    private AtomicInteger atomicValue = new AtomicInteger(0);
    public void increment() {
        value++;
        atomicValue.getAndIncrement();
    }
    public void printValues() {
        System.out.println("value       : " + value);
        System.out.println("atomicValue : " + atomicValue.get());
    }
}
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter obj = new Counter();
        Runnable r = () ->
            { for (int i = 0; i < 1000; i++) { obj.increment(); }};
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start(); t2.start();
        t1.join(); t2.join();
        obj.printValues();
    }
}