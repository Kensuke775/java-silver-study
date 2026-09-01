import java.util.stream.IntStream;

public class Main {
    public static void main(String... args) {
        var stream = IntStream.of(0, 5, 5, 0, 10, 10);
        IntStream is1 = stream.distinct();
        IntStream is2 = is1.skip(1L);
        is2.forEach(System.out::print);                 // (A)
        System.out.println(" " + is2.count());          // (B)
    }
}