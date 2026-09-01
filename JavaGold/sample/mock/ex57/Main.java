import java.util.*;

public class Main {
    public static void main(String... args) {
        Map<Item, String> mapEn = new TreeMap<>();
        mapEn.put(new Item(1), "One");
        mapEn.put(new Item(3), "Three");
        mapEn.put(new Item(2), "Two");
        Map<Item, String> mapFr = new HashMap<>();
        mapFr.put(new Item(3), "Trois");
        mapFr.put(new Item(2), "Deux");
        mapEn.putAll(mapFr);

        for (var e : mapEn.entrySet()) {
            System.out.print(e.getKey().id() + ":"
                    + e.getValue() + " ");
        }
    }
}
record Item(int id) implements Comparable<Item> {
    @Override
    public int compareTo(Item o) { return this.id() - o.id(); }
}