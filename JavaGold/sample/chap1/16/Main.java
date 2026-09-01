import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Zero"); map.put(10, "Zero");
        map.put(20, "Twenty");
        System.out.println(map);
        System.out.println(" put(10, \"Ten\") : "
                            + map.put(10, "Ten"));
        System.out.println(" remove(20)     : " + map.remove(20));
        System.out.println(" remove(30)     : " + map.remove(30));
        System.out.println(" containsKey(20): "
                            + map.containsKey(20));
        System.out.println(" containsValue(\"Ten\"): "
                            + map.containsValue("Ten"));
        Set<Integer> keyset = map.keySet();
        Collection<String> values = map.values();
        Set<Map.Entry<Integer, String>> entryset = map.entrySet();
        System.out.println("Keys     : " + keyset);
        System.out.println("Values   : " + values);
        System.out.println("Map.Entry: " + entryset);
        System.out.print("Map.Entry... ");
        for (Map.Entry<Integer, String> e : entryset) {
            System.out.print(e.getKey() + ":" + e.getValue() + " ");
        }
    }
}