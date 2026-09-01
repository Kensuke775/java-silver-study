import java.util.function.*;

public class Main {
    public static void main(String... args) {
        MyInterface obj = String::length;
        var result = obj.count("Hello Duke!");
        System.out.println(result);
    }
}
@FunctionalInterface
interface MyInterface { int count(String text); }