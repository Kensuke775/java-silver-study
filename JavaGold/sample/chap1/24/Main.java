import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<String> list =
                new ArrayList<>(Arrays.asList("x", "y"));
        list.add("z");
        System.out.println("list : " + list);
        Set<Integer> set =
                new HashSet<>(Set.of(8, 11, 17));
        set.remove(8);
        System.out.println("set  : " + set);
        Map<Integer, String> map =
                new HashMap<>(Map.of(1, "First", 2, "Second"));
        map.put(3, "Third");
        System.out.println("map  : " + map);
    }
}