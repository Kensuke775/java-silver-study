import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3);
        Consumer<List<Integer>> con = System.out::println;
        con.accept(list);
        Predicate<String> pre = s -> s.length() < 5;
        boolean result = pre.test("Duke");
        System.out.println("result: " + result);

        String text = "Lambda and Stream API";
        BiFunction<String, String, Integer> bif1 =
                                    (src, tgt) -> src.indexOf(tgt);
        BiFunction<String, Integer, Character> bif2 = String::charAt;
        int index = bif1.apply(text, "S");      // 11
        char ch = bif2.apply(text, 15);         // a
    }
}