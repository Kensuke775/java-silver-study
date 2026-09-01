import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("Red", "Blue", "Black");
        Predicate<List<String>> isEmpty = List::isEmpty;
        Predicate<List<String>> lessThan5 = l -> l.size() < 5;
        Predicate<List<String>> containsRed = l -> l.contains("Red");
        boolean b1 = isEmpty.and(lessThan5).test(list);
        boolean b2 = isEmpty.or(lessThan5).negate().test(list);
        System.out.println("b1: " + b1);            // false
        System.out.println("b2: " + b2);            // false

        boolean boolResult = list.contains("Red")
                || list.isEmpty()
                && !(list.size() < 5);
        boolean predResult =
                containsRed.or(isEmpty).and(lessThan5).negate()
                .test(list);
        System.out.println("boolResult: " + boolResult);
        System.out.println("predResult: " + predResult);
    }
}