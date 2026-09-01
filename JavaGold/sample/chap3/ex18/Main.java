import java.util.List;

public class Main {
    public static void main(String... args) {
        List<Book> list = List.of(
            new Book("TECH", "Java basic", 25.00),
            new Book("TECH", "Java beginner", 20.50),
            new Book("LANG", "English Grammar", 10.50),
            new Book("LANG", "Basic Japanese", 12.00),
            new Book("ART", "Discovering Art", 40.50)
            );
    }
}
record Book(String genre, String title, double price) {}