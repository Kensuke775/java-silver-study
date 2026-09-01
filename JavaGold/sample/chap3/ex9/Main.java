import java.util.List;
public class Main {
    public static void main(String... args) {
        List<Book> books = List.of(
                new Book(30, "Java-intermediate"),
                new Book(60, "Exam guide for Java"),
                new Book(10, "Java-basic"));
        books.stream()
                .sorted()
                .mapToInt(b -> b.id())
                .forEach(System.out::print);
    }
}
record Book(int id, String name){}