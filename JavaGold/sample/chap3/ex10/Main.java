import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String... args) {
        List<Book> list = List.of(
                new Book("Java-intermediate", 11),
                new Book("Exam guide for Java", 11),
                new Book("Java-basic", 17),
                new Book("History of Japan", 5));
        Comparator<Book> comp1 =
                        Comparator.comparingInt(b -> b.stock());
        Comparator<Book> comp2 =
                        Comparator.comparing(b -> b.name());
        List<Book> sortedList = list.stream()
                .filter(b -> b.stock() > 5)
                .sorted(comp1.thenComparing(comp2))
                .limit(3L)
                .toList();
    }
}
record Book(String name, int stock) {}