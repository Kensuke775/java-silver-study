import java.util.List;

public class Main {
    public static void main(String... args) {
        List<String> codes = List.of("US", "JP", "AU", "GB");
        long count = codes.stream()
                .sorted()
                .dropWhile(s -> s.contains("U"))
                .peek(s -> System.out.print(s + " "))
                .count();
        System.out.println(count);
    }
}