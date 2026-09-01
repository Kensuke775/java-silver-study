import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;

public class Main {
    public static void main(String[] args) {
        Consumer<Integer> con = i -> System.out.print(i + 20); // 22
        con.accept(2);
        IntConsumer iCon = i -> System.out.print(i + 10);      // 11
        iCon.accept(1);

     // DoubleSupplier ds = () -> Math.random();
        DoubleSupplier ds = Math::random;
        double d = ds.getAsDouble();
        IntFunction<String[]> if1 = length -> new String[length];
        String[] ary = if1.apply(5);
        IntFunction if2 = String[]::new;
        Object ok = if2.apply(5);
     // String[] ng = if2.apply(5);
    }
}