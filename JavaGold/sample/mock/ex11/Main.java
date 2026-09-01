import java.util.List;

public class Main {
    public static void main(String... args) {
        List<Country> countries = List.of(
                new Country("JP", "Japan"),
                new Country("GB", "United Kingdom"),
                new Country("FR", "France"),
                new Country("US", "United States"));
        countries.stream()
                .map(e -> e.name())
                .dropWhile(s -> s.contains(" "))
                .forEach(s -> System.out.print(s + " "));
    }
}
record Country(String code, String name) {}