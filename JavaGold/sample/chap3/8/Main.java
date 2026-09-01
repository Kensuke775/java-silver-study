import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Clothes(String name, int size) {}
public class Main {
    public static void main(String[] args) {
        List<Clothes> clothes = List.of(
                new Clothes("Jacket", 1),
                new Clothes("Shirt", 2),
                new Clothes("Dress", 1));
        Stream<Clothes> streamAll = clothes.stream();
        Stream<Clothes> streamSize1
                        = streamAll.filter(c -> c.size() == 1);
        Stream<String> sStream = streamSize1.map(c -> c.name());
        IntStream iStream = sStream.mapToInt(s -> s.length());
        iStream.forEach(i -> System.out.print(i));          // 65
        System.out.println();

        clothes.stream()                            // Stream<Clothes>
            .filter(c -> c.size() == 1)
            .peek(s -> System.out.print("peek1:" + s + " "))
            .map(c -> c.name())                     // Stream<String>
            .peek(s -> System.out.print("peek2:" + s + " "))
            .mapToInt(s -> s.length())              // IntStream
            .forEach(i -> System.out.println("/ forEach:" + i));
    }
}