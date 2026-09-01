import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<Integer> list1 = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> list2 = new ArrayList<>(list1);
        Predicate<Integer> p1 = (var i) -> i % 2 == 0;  // (A)
        Predicate<Integer> p2 = (var i) -> i % 5 == 0;  // (B)
        Predicate<Integer> filter = p1.or(p2).negate();
        list2.removeIf(filter);
        System.out.println(list2);
    }
}