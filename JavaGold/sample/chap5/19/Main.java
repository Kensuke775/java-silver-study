import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("Duke", "Sara", "James", "Lucy");
        Optional<String> any = list.parallelStream()
                .filter(s -> s.contains("a")).findAny();
        Optional<String> first = list.parallelStream()
                .filter(s -> s.contains("a")).findFirst();
        System.out.println("findAny()  : " + any.get());
        System.out.println("findFirst(): " + first.get());

        IntStream stream1 = IntStream.rangeClosed(0, 5).parallel();
        System.out.print("forEach()       : ");
        stream1.forEach(System.out::print);
        System.out.print("\nforEachOrdered(): ");
        IntStream stream2 = IntStream.rangeClosed(0, 5).parallel();
        stream2.forEachOrdered(System.out::print);
    }
}