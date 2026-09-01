import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Runnable task = () -> {
            pause(1000L);
            System.out.println("[Runnable] " + getLocalTime());
        };
        ScheduledExecutorService ses =
                Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(task, 2L, 3L, TimeUnit.SECONDS);
     // ses.scheduleWithFixedDelay(task, 2L, 3L, TimeUnit.SECONDS);
        pause(10000L);
        ses.shutdown();
    }
    static String getLocalTime() {
        LocalTime now = LocalTime.now();
        return now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}
    }
}