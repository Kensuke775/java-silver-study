import java.util.function.*;

public class Main {
    public static void main(String... args) {
        Consumer obj = o -> { System.out.print(o); };       // (A)
        obj.accept(new Item());
    }
}
class Item { private int id = 100; }