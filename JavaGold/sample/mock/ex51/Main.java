import java.util.*;

public class Main {
    public static void main(String... args) {
        Map<String, String> map = new TreeMap<>(Map.of("US", "Duke"));
        map.merge("CA", "James", (a, b) -> a + ":" + b);
        map.merge("US", "Carol", (a, b) -> a + ":" + b);
        map.merge("FR", "Eric", (a, b) -> a + ":" + b);
        map.merge("CA", "Julian", (a, b) -> a + ":" + b);
        System.out.println(map);
    }
}