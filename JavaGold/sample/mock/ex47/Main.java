import java.util.*;
import java.util.function.*;

public class Main {
    public static void main(String... args) {
        UnaryOperator<String> operate = new UnaryOperator<String>() {
            @Override
            public String apply(String s) { return s.toUpperCase(); }
        };
        List<String> list = Arrays.asList("Duke", "Anna", "James");
        list.replaceAll(operate);
        System.out.println(list);
    }
}