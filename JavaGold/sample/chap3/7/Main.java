import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("Linda ", "Emily ", "James ");
        Stream<String> stream = list.stream();  // Stream<String>
        stream.map(String::toUpperCase)         // Stream<String>
                .forEach(System.out::print);
    }
}