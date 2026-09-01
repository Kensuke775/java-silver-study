import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String... args) throws InterruptedException {
        AtomicDecrementer obj = new AtomicDecrementer();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            threads.add(new Thread(obj::decrement));
            threads.get(i).start();
        }
        for (Thread t : threads) { t.join(); }
        System.out.println(obj.getValue().getAndAdd(5));
    }
}
class AtomicDecrementer {
    private AtomicInteger value = new AtomicInteger(100);
    public void decrement() { value.decrementAndGet(); }
    public AtomicInteger getValue() { return value; }
}