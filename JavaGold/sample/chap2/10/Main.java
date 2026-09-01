import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@FunctionalInterface interface Operator { int operate(String s); }
public class Main {
    public static void main(String[] args) {
        // staticメソッド参照
        Operator op1 = s -> Integer.parseInt(s);
        Operator op2 = Integer::parseInt;
        op2.operate("100");
        // インスタンスメソッド参照 (任意のオブジェクト)
        String name = "Duke";
        Operator op3 = s -> name.compareTo(s);
        Operator op4 = name::compareTo;
        op4.operate("James");              // -6
        // インスタンスメソッド参照 (引数のオブジェクト)
        Operator op5 = s -> s.length();
     // Operator ng = s::length;
        Operator op6 = String::length;
        op6.operate("Duke");               // 4

        Comparator<String> comp1 = (s1, s2) -> s1.compareToIgnoreCase(s2);
        Comparator<String> comp2 = String::compareToIgnoreCase;
        comp2.compare("A", "B");

        List<String> list = Arrays.asList("D", "u", "k", "e");
/*      Collections.sort(list,
                (o1, o2) -> o1.compareToIgnoreCase(o2));    */
        Collections.sort(list, String::compareToIgnoreCase);
    }
}