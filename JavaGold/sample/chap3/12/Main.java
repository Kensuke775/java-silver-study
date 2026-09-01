import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Main {
    public static void main(String... args) {
        List<String> list = List.of("Duke", "Anna", "Paul");
        Optional<String> o1 = list.stream().findAny();
        Optional<String> o2 = list.stream().findFirst();
        System.out.println("findAny()  : " + o1.get());
        System.out.println("findFirst(): " + o2.orElse("empty"));
        OptionalInt oInt1 =
            IntStream.range(1, 5).filter(i -> i % 2 == 0).findAny();
        OptionalInt oInt2 =
            IntStream.range(1, 5).filter(i -> i > 10).findFirst();
        System.out.println("findAny()  : " + oInt1.getAsInt());
        System.out.println("findFirst(): " + oInt2.orElse(0));
        
        Predicate<String> pred = s -> s.length() == 4;
        boolean b1 = list.stream().allMatch(pred);          // true
        boolean b2 = list.stream().anyMatch(pred);          // true
        boolean b3 = list.stream().noneMatch(pred);         // false
        System.out.println("b1:" + b1 + " b2:" + b2 + " b3:" + b3);
    }
}