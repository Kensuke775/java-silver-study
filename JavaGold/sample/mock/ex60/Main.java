import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String... args) {
        List<Item> items = List.of(
                new Item("Coffee", "Drink", 2500),
                new Item("Tea", "Drink", 1500),
                new Item("Bread", "Food", 400),
                new Item("Cheese", "Food", 800),
                new Item("Chocolate", "Snack", 1000),
                new Item("Nuts", "Snack", 500));
        Map<String, String> map = items.stream()
                // insert code here
        map.forEach((k, v) -> System.out.print(" [" + k + "] " + v));
    }
}
record Item(String name, String category, int price) {}