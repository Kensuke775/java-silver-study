import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@FunctionalInterface
interface Greeting { void hello(String s); }
public class Main {
    public static void main(String[] args) {
        Greeting english = s -> System.out.println(s);
        english.hello("Hello!");
        Greeting french = (var v) -> System.out.println(v);
        french.hello("Bonjour!");
        Runnable obj = () -> System.out.println("run()");
        obj.run();

        List<String> list = Arrays.asList("D", "u", "k", "e");
        Collections.sort(list,
                (o1, o2) -> o1.compareToIgnoreCase(o2));
        System.out.println("list: " + list);
    /*  Collections.sort(list,
                (o1, o2) -> return o1.compareToIgnoreCase(o2));
        Collections.sort(list,
                (o1, o2) -> { o1.compareToIgnoreCase(o2)) };  */
    }
}