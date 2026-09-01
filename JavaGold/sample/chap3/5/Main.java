import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        IntStream.rangeClosed(0, 10)
                .skip(3L)
                .limit(3L)
                .forEach(System.out::print);        // 345
        System.out.println();
        IntStream.rangeClosed(0, 3)
                .skip(5L)
                .forEach(System.out::print);
        System.out.println();
        IntStream.rangeClosed(0, 3)
                .limit(5L)
                .forEach(System.out::print);        // 0123
        System.out.println();

        DoubleStream dStream = DoubleStream.generate(Math::random);
        dStream.limit(2L).forEach(s -> System.out.print(s + " "));
        System.out.println();
        IntStream iStream1 = IntStream.iterate(0, i -> i + 2);
        iStream1.limit(10L).forEach(s -> System.out.print(s + " "));
        System.out.println();
        IntStream iStream2 =
                IntStream.iterate(0, i -> i < 20, i -> i + 2);
        iStream2.forEach(s -> System.out.print(s + " "));
    }
}