import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        Map<Integer, String> map =
                new ConcurrentHashMap<>(Map.of(0, "ZERO"));
        Runnable r1 = () -> {
            for (int i = 0; i < 5; i++) {
                map.putIfAbsent(i, "number"+ i);
            }
        };
        Runnable r2 = () -> {
            for (int i = 2; i < 5; i++) {
                map.computeIfAbsent(i, key -> "number"+ (key + 100));
            }
        };
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start(); t2.start();
        try {
            t1.join(); t2.join();
        } catch (InterruptedException e) {}
        map.forEach((k, v) ->
                System.out.println("Key:" + k + " Value:" + v));
    }
}