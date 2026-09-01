import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 10, 5, 0);
        System.out.println("addAll() : " + list);
        Collections.reverse(list);
        System.out.println("reverse(): " + list);
        Collections.sort(list);
        System.out.println("sort()   : " + list);
        System.out.println("binarySearch(): "
                    + Collections.binarySearch(list, 10));
        List rawList = new ArrayList();
        Collections.addAll(rawList, 0, "One", 1.5);
        System.out.println("rawList  : " + rawList);
     // Collections.sort(rawList);  // ClassCastException
    }
}