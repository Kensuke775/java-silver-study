import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = List.of(0, 1, 2, 3);
        Set<String> set = Set.of("Zero", "One", "Two");
        Map<Integer, String> map = Map.of(1, "First", 2, "Second");
        System.out.println(list);
        System.out.println(set);
        System.out.println(map);
     // list.add(4);
     // Set<Object> set1 = Set.of(null);
     // Set<String> set2 = Set.of("A", "A");
     // Map<Integer, String> map = Map.of(0, "A", 0, "B");
    }
}