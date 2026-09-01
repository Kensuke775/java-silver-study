import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String... args) {
        List<String> winterClothes = List.of("Sweater", "Jeans");
        List<String> summerClothes = List.of("T-shirt", "Jeans");
        List<List<String>> allClothes =
                            List.of(winterClothes, summerClothes);
        Stream<List<String>> stream = allClothes.stream();
        stream.flatMap(l -> l.stream())
                .distinct()
                .peek(e -> System.out.println("debug: " + e))
                .filter(e -> e.length() > 5)
                .forEach(System.out::println);
    }
}