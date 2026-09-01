import java.util.*;

public class Main {
    public static void main(String... args) {
        List<Item> items = List.of(
                new Item("Coffee", 2500),
                new Item("Bread", 400),
                new Item("Cheese", 800),
                new Item("Chocolate", 1000),
                new Item("Nuts", 500),
                new Item("Tea", 1500));
        boolean result = items.parallelStream()
                            // insert code here
    }
}
record Item(String name, int price) {}