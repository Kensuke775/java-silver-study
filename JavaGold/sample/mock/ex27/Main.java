import java.util.*;

public class Main {
    public static void main(String... args) {
        List<String> names = new ArrayList<>();                 // (A)
        names.addAll(List.of("Amy", "Duke", "Carol", "Eric"));
        Runnable r1 = () -> names.forEach(System.out::println); // (B)
        Runnable r2 = () -> names.replaceAll(String::toUpperCase);
        new Thread(r1).start();
        new Thread(r2).start();
    }
}