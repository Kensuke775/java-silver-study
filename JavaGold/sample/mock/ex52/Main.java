import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String... args) {
        Set<Book> books = Set.of(
                new Book("Java basic", 30.00),
                new Book("Java beginner", 23.50),
                new Book("English Grammar", 40.00),
                new Book("Basic Japanese", 25.00),
                new Book("Discovering Art", 21.50));
        double result = // insert code here
    }
}
record Book(String title, double price){}