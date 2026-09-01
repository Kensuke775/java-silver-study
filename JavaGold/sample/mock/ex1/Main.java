import java.util.*;

public class Main {
    public static void main(String... args) {
        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4);
        int[] array = {5, 6, 7, 8, 9};
        var stmA = list.stream().mapToInt(e -> e);
        var stmB = Arrays.stream(array);
        // insert code here
    }
}