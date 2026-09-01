import java.util.ArrayList;
import java.util.List;

public class Main {
    static void testUpperBnd(List<? extends Number> list) {
        list.add(null);
     // list.add(Integer.valueOf(10));
        Number n = list.get(0);
    }
    static void testLowerBnd(List<? super Number> list) {
        list.add(Integer.valueOf(10));
     // list.add(new Object());
        Object o = list.get(0);
     // Number n = list.get(1); Integer i = list.get(2);
    }
    public static void main(String[] args) {
        List<Object> oList = new ArrayList<>(); oList.add(1);
        List<Number> nList = new ArrayList<>(); nList.add(1);
        List<Integer> iList = new ArrayList<>(); iList.add(1);
     // testUpperBnd(oList);
        testUpperBnd(nList);
        testUpperBnd(iList);
        testLowerBnd(oList);
        testLowerBnd(nList);
     // testLowerBnd(iList);
    }
}