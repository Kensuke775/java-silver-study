import java.util.*;

public class Main {
    public static void main(String... args) {
        Person p1 = new Person("Louis", "FR");
        Person p2 = new Person("James", "CA");
        Person p3 = new Person("Alberto", "ES");
        Person p4 = new Person("Amanda", "CA");
        List<Person> list = List.of(p1, p2, p3, p4);
        list.sort(Comparator.comparing(Person::country)     // (A)
                .thenComparing(Person::name)
                .reversed());                               // (B)
        list.forEach(p -> System.out.print(p.name() + " "));
    }
}
record Person(String name, String country){}