import java.util.*;

public class Main {
    public static void main(String... args) {
        List<String> countries =
                List.of("Japan", "France", "Canada", "Spain");

        var result = countries.stream()
                .filter(c -> c.startsWith("A"))
                .findAny();
        System.out.println( /* [    (1)    ] */ );
    }
}