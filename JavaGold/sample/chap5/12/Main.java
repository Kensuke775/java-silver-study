import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Runnable task = () -> {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {}
            System.out.println(Thread.currentThread().getName());
        };
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(task);
        es.execute(task);
        es.shutdown();
        try {
            if(!es.awaitTermination(100L, TimeUnit.MILLISECONDS)) {
                System.out.println(" calling shutdownNow()...");
                es.shutdownNow();
            }
        } catch (InterruptedException e) {
            es.shutdownNow();
        }
        System.out.println(" isShutdown()  : " + es.isShutdown());
        System.out.println(" isTerminated(): " + es.isTerminated());
    }
}