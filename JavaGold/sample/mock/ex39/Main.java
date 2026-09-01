import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String... args) {
        List<String> list = List.of("es", "de", "gb", "us", "ch");
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Long> result = executor.submit(() -> {
            return list.stream()
                    .map(String::toUpperCase)
                    .filter(s -> s.contains("S"))
                    .count();
        });
        executor.shutdown();
        try {
            System.out.println("Countries: " + /* [    (1)    ] */);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}