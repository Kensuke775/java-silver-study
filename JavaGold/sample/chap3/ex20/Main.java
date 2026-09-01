import java.util.*;
import java.util.stream.*;
public class Main {
    public static void main(String... args) {
        List<Fruit> items = List.of(
                new Fruit(2, "Kiwi", 10), new Fruit(4, "Orange", 3),
                new Fruit(3, "Lemon", 4), new Fruit(1, "Apple", 7));
        var map = items.stream()
                .sorted(Comparator.comparingInt(Fruit::id))
                .collect(Collectors.partitioningBy(
                    e -> e.stock() > 10,
                    Collectors.mapping(                 // (A)
                            Fruit::name,
                            Collectors.joining(", "))   // (B)
                ));
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
record Fruit(int id, String name, int stock) {}