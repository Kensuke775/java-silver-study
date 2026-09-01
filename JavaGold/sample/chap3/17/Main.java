import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

record Clothes(int id, String name, int stock) {}
public class Main {
    public static void main(String[] args) {
        List<Clothes> clothes = List.of(
            new Clothes(1, "Shirt", 1), new Clothes(2, "Jacket", 7),
            new Clothes(3, "Jacket", 5), new Clothes(4, "Coat", 5));
        Map<Integer, String> clothes1 = clothes.stream()
            .collect(Collectors.toMap(
                    c -> c.id(), c -> c.name()));
        Map<String, Integer> clothes2 = clothes.stream()
            .collect(Collectors.toMap(
                    Clothes::name, Clothes::stock,
                    (v1, v2) -> v1 + v2));
        Map<String, Integer> clothes3 = clothes.stream()
            .collect(Collectors.toMap(
                    Clothes::name, Clothes::stock,
                    (v1, v2) -> v1 + v2,
                    TreeMap::new));
        System.out.println("id=name   : " + clothes1);
        System.out.println("name=stock: " + clothes2);
        System.out.println("name=stock: " + clothes3);
    }
}