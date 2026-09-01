import java.util.stream.IntStream;

public class Main {
	public static void main(String[] args) {
        IntStream stream = IntStream.range(0, 10);
        stream.filter(n -> n % 2 == 0)
                .forEach(System.out::print);
     // stream.filter(n -> n <= 5);     // IllegalStateException
		System.out.println();
        IntStream.range(0,  10)
                .dropWhile(n -> n <= 2)
                .takeWhile(n -> n <= 7)
                .forEach(System.out::print);
        System.out.println("\n-----");
        IntStream.of(0, 2, 4)
                .takeWhile(i -> i % 2 != 0)
                .forEach(System.out::print);
    }
}