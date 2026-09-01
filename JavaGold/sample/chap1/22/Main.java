import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] str = {"A", "B", "C"};
        List<String> list1 = Arrays.asList(str);
        List<Integer> list2 = Arrays.asList(8, 11, 17);
     // ArrayList<Long> list3 = Arrays.asList(10L);
        str[0] = "Alice"; list1.set(2, "Carol");
        System.out.println("list1: " + list1);
        list2.add(21);   // UnsupportedOperationException
        // List to Array
        List<String> stList = Arrays.asList("List", "to", "Array");
        String[] stArray = stList.toArray(new String[stList.size()]);
    }
}