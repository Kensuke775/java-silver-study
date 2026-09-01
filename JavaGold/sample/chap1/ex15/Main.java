import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class Main {
    public static void main(String... args) {
        Integer[] data = {1, 0};
        List<Integer> a = Arrays.asList(data);
        List<Integer> b = List.of(data);
        var c = Arrays.asList("1", "0");
        Collections.sort(a);
        Collections.sort(b);
        Collections.sort(c);
        System.out.println(a + "" + b + "" + c);
    }
}