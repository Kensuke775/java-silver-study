import java.util.HashMap;
import java.util.Map;
public class Main {
    public static void main(String... args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("0", 0); map.put("1", 1); map.put("2", 2);
        System.out.print(map.put("1",null) + " ");
        System.out.print(map.get("1") + " ");
        System.out.print(map.get(2) + " ");
        if(map.containsValue(1)) map.clear();
        System.out.print(map.remove("2") + " ");
    }
}