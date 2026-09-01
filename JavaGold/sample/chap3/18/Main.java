import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

record Clothes(String name, int size, int stock) {}
public class Main {
    public static void main(String[] args) {
        List<Clothes> clothes = List.of(
            new Clothes("Shirt", 1, 7), new Clothes("Jacket", 1, 4),
            new Clothes("Jeans", 2, 5), new Clothes("Coat", 2, 2));

        Map<Integer, List<Clothes>> clothes1 =
                clothes.stream()
                    .collect(Collectors.groupingBy(
                            Clothes::size));
        System.out.println("GROUPING:size:");
        clothes1.forEach((key, value)
                -> System.out.println(key + ": " + value));
        Map<Integer, Integer> clothes2 =
                clothes.stream()
                    .collect(Collectors.groupingBy(
                            Clothes::size,
                            Collectors.summingInt(Clothes::stock)));
        System.out.println("GROUPING:size,SUMMING:stock: " + clothes2);
        Map<Character, List<String>> clothes3 =
                clothes.stream()
                    .map(Clothes::name)
                    .collect(Collectors.groupingBy(
                            s -> s.charAt(0),
                            TreeMap::new,
                            Collectors.toList()));
        System.out.println("GROUPING:charAt(0),toList  : " + clothes3);

/*
        // averagingInt()
        Map<Integer, Double> avg =
                clothes.stream()
                    .collect(Collectors.groupingBy(
                            Clothes::size,
                            Collectors.averagingInt(Clothes::stock)));
        System.out.println("GROUPING:size,AVGING:stock : " + avg);
 */
    }
}