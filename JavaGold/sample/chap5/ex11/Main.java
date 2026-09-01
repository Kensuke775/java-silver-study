import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String... args) {
        Runnable rTask = () -> System.out.println("Runnable");
        Callable<String> cTask = () -> "Callable";
        ExecutorService service = Executors.newFixedThreadPool(2);
        // insert code here
        service.shutdown();
    }
}