import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Runnable rTask = () ->
                System.out.println("[Runnable] " + getLocalTime());
        Callable<String> cTask = () -> "[Callable] " + getLocalTime();
        ScheduledExecutorService ses =
                Executors.newSingleThreadScheduledExecutor();
        System.out.println("[Main]     " + getLocalTime());
        ses.schedule(rTask, 1L, TimeUnit.SECONDS);
        ses.schedule(rTask, 2L, TimeUnit.SECONDS);
        ScheduledFuture<?> result1 =
                            ses.schedule(rTask, 5L, TimeUnit.SECONDS);
        ScheduledFuture<String> result2 =
                            ses.schedule(cTask, 5L, TimeUnit.SECONDS);
        try {
            System.out.println("result1: " + result1.get());
            System.out.println("result2: " + result2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            ses.shutdown();
        }
    }
    static String getLocalTime() {
        LocalTime now = LocalTime.now();
        return now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}