import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        Supplier<StringBuilder> s1 = () -> new StringBuilder();
        Supplier<StringBuilder> s2 = StringBuilder::new;
        StringBuilder sbEmpty = s2.get();
        Function<String, StringBuilder> f1 =
                                    s -> new StringBuilder(s);
        Function<String, StringBuilder> f2 = StringBuilder::new;
        StringBuilder sbText = f2.apply("Hello!");
        System.out.println("sbText: " + sbText);

        Function<Integer, String[]> f3 = n -> new String[n];
        Function<Integer, String[]> f4 = String[]::new;
        String[] ary = f4.apply(3);
        System.out.println("ary.length: " + ary.length);
    }
}