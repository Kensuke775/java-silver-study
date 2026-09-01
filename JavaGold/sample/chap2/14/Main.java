import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<String> list =
                new ArrayList<>(List.of("jp ", "gb ", "us "));
        list.replaceAll(String::toUpperCase);
        list.removeIf(s -> s.startsWith("J"));
        list.forEach(System.out::print);
     // for (String s : list) { System.out.print(s);}
        System.out.println();
        
        Map<Integer, String> base = Map.of(1, "One", 2, "Two");
        Map<Integer, String> map1 = new HashMap<>(base);
        map1.compute(1, (k, v) -> v + "/Updated");
        map1.compute(2, (k, v) -> null);
        System.out.println("map1.compute(): " + map1);
        Map<Integer, String> map2 = new HashMap<>(base);
        map2.merge(2, "Deux", (v1, v2) -> v1 + "/" + v2);
        map2.merge(3, "Trois", (v1, v2) -> v1 +  "/" + v2);
        System.out.println("map2.merge()  : " + map2);
    }
}