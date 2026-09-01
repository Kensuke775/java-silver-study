class Foo<T extends Number> {}
class Bar<T, X extends T> {}
public class Main {
    public static void main(String[] args) {
        Foo<Integer> foo = new Foo<>();
     // Foo<Object> err = new Foo<>();
        Bar<Number, Double> bar = new Bar<>();
    }
}