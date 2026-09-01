import java.util.*;

public class Main {
    public static void main(String... args) {
        Set<String> set = Set.of("Coat", "Jeans", "Dress", "Pants");
        set.stream()
                .filter(e -> e.length() > 4)
                .map(String::toUpperCase)
                .forEachOrdered(e -> System.out.print(e + " "));
    }
}