import java.util.*;

public class Main {
    public static void main(String... args) {
        List<String> list = List.of("pig", "dog", "pig", "cat", "fox");
        var animal = new TreeSet<>(list);           // (A)
        System.out.println(animal);
    }
}