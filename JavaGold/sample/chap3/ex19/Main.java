import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String... args) {
        String[] names = {"Duke", "Carol", "James", "Ivan", "Amy"};
        Map<Integer, String> map = Arrays.stream(names)
                .collect(Collectors.toMap(String::length, s -> s));
        System.out.println(map);
    }
}