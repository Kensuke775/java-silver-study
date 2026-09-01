import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("a", "b", "c");
        Map<Integer, String> map = Map.of(1, "x", 2, "y");
        int[] ary = {10, 20, 30};
        Stream<String> listStream = list.stream();
        Stream<Map.Entry<Integer, String>> mapStream =
                                map.entrySet().stream();
        IntStream aryStream1 = Arrays.stream(ary);
        IntStream aryStream2 = Arrays.stream(ary, 0, 3);

        Stream<String> s1 = Stream.empty();
        Stream<String> s2 = Stream.of("Duke", "James");
     // Stream<String> ng = Stream.of(null);    // NullPointerException
        Stream<String> s3 = Stream.ofNullable(null);
    }
}