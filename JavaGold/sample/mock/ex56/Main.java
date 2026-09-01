import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String... args) {
        List<Book> books = List.of(
                new Book("Java Basic", "TECH", 5),
                new Book("Java Advanced", "TECH", 0),
                new Book("English Grammar", "LANG", 8),
                new Book("Basic Japanese", "LANG", 12),
                new Book("Art of Painting", "ART", 0));
        Map<String, List<String>> result =
                books.stream()
                    .filter(e -> e.stock() == 0)
                    .collect(Collectors.groupingBy(
                            Book::category,
                            Collectors.mapping(
                                    Book::title, Collectors.toList())
                    ));
        System.out.println(result);
    }
}
record Book(String title, String category, int stock) {}