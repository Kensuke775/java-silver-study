import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        String[] persons = {"Carol", "Paul", "Amy"};
        Arrays.stream(persons)
                .sorted()
                .forEach(s -> System.out.print(s + " "));
        System.out.println();
        Arrays.stream(persons)
                .sorted(Comparator.reverseOrder())
                .forEach(s -> System.out.print(s + " "));
    }
}