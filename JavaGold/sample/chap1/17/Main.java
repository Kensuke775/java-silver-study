import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        // LinkedHashMap
        Map<Integer, String> map1 = new LinkedHashMap<>();
        map1.put(2, "Two"); map1.put(0, "Zero");
        map1.put(3, "Three"); map1.put(1, "One");
        System.out.println("LinkedHashMap: " + map1);
        // TreeMap
        Map<Integer, String> map2 = new TreeMap<>();
        map2.put(2, "Two"); map2.put(0, "Zero");
        map2.put(3, "Three"); map2.put(1, "One");
        System.out.println("TreeMap      : " + map2);
    }
}