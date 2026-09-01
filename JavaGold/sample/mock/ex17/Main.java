import java.time.LocalDate;
import java.util.function.Supplier;

public class Main {
    public static void main(String... args) {
        Supplier<LocalDate> obj = LocalDate::now;
        System.out.println( /* [    (1)    ] */ );
    }
}