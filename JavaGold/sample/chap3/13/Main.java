import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("Duke", "Robert", "Amy");
        Comparator<String> comp = (s1, s2) -> s1.length() - s2.length();
        Optional<String>  max = list.stream().max(comp);
        System.out.println("max  : " + max.get());
        OptionalInt min = IntStream.of(21, 8, 17).min();
        System.out.println("min  : " + min.getAsInt());

        long count = list.stream().count();
        OptionalDouble avg = IntStream.rangeClosed(1, 10).average();
        int sum = IntStream.rangeClosed(1, 10).sum();
        System.out.println("count: " + count);
        System.out.println("avg  : " + avg.getAsDouble());
        System.out.println("sum  : " + sum);
    }
}