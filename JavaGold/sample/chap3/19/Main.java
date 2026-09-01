import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

record Clothes(String name, int stock) {}
public class Main {
    public static void main(String[] args) {
        List<Clothes> clothes = List.of(
            new Clothes("Shirt", 7), new Clothes("Jacket", 4),
            new Clothes("Jeans", 5), new Clothes("Coat", 2));
            
        Map<Boolean, List<Clothes>> clothes1 =
                clothes.stream()
                    .collect(Collectors.partitioningBy(
                                c -> c.stock() >= 5));
        System.out.println("stock >= 5:");
        clothes1.forEach((key, value)
                -> System.out.println(key + ": " + value));
        Map<Boolean, Long> clothes2 =
                clothes.stream()
                    .collect(Collectors.partitioningBy(
                                c -> c.stock() == 0,
                                Collectors.counting()));
        System.out.println("stock == 0: " + clothes2);
    }
}