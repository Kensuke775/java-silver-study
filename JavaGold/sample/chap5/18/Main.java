import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("a", "b", "c");
        Stream<String> stream1 = list.parallelStream();
        Stream<String> stream2 = list.stream().parallel();
        Stream<String> stream3 = list.parallelStream().sequential();
        System.out.println(stream1.isParallel());    // true
        System.out.println(stream2.isParallel());    // true
        System.out.println(stream3.isParallel());    // false
    }
}