import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String... args) {
        List<Number> aList = new ArrayList<>();     // (A)
        List<int[]> bList = new ArrayList<>();      // (B)
        List<> cList = method();                    // (C)
        method(new ArrayList<>());
    }
    public static List<Integer> method() {
        return new ArrayList<>();                   // (D)
    }
    public static void method(List<Number> list) {
        list = new ArrayList<Integer>();            // (E)
    }
}