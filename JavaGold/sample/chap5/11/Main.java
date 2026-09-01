import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Runnable task = () ->
                System.out.println(Thread.currentThread().getName());
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(task);
        es.execute(task);
        es.shutdown();
        System.out.println(" isShutdown()  : " + es.isShutdown());
        System.out.println(" isTerminated(): " + es.isTerminated());
    }
}