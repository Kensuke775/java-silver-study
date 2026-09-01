import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(10); list.add(20); list.add(null);
        for (Integer i : list) System.out.print(i + " ");
        System.out.println("\nsize(): " + list.size());
        System.out.println("set() : " + list.set(2, 20));
        System.out.println("contains(): " + list.contains(50));
        System.out.println("indexOf() : " + list.indexOf(20));
        System.out.println(list);
        list.add(0, 100);
        System.out.println("remove(): " + list.remove(1));
        System.out.println("remove(): "
                        + list.remove(Integer.valueOf(20)));
     // System.out.println("remove(): " + list.remove(20));
        System.out.println(list);
    }
}