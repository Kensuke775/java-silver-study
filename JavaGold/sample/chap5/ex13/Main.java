import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String... args) {
        CyclicBarrier barrier = new CyclicBarrier(2,
                () -> System.out.println("<< PASSED >>"));
        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName());
            try {
                barrier.await(5, TimeUnit.SECONDS);         // (A)
            } catch (Exception e) { e.printStackTrace(); }
        };
        ExecutorService service = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 5; i++) {
                service.execute(task);
            }
            service.shutdown();
        } catch (Exception e) { e.printStackTrace(); }
    }
}