import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class MyCallable implements Callable<String> {
    @Override
    public String call() {
        String name = Thread.currentThread().getName();
        return "[Returning from Callable] " + name;
    }
}
public class Main {
    public static void main(String[] args) {
        Callable<String> task = new MyCallable();
        ExecutorService es = Executors.newCachedThreadPool();
        Future<String> result1 = es.submit(task);
        Future<String> result2 = es.submit(task);
        try {
            String s1 = result1.get();
            String s2 = result2.get(5L, TimeUnit.SECONDS);
            System.out.println("s1: " + s1);
            System.out.println("s2: " + s2);
        } catch (InterruptedException
                | ExecutionException
                | TimeoutException e) {
            e.printStackTrace();
                } finally {
            es.shutdown();
        }
    }
}