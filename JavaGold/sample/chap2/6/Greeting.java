@FunctionalInterface
public interface Greeting {
    void hello();
    default void print() { disp(); }
    static void show() { disp(); }
    private static void disp() {
        System.out.println("Greeting interface");
    }
    @Override String toString();
    @Override int hashCode();
    @Override boolean equals(Object o);
 // void func();
}
class Main {
    public static void main(String[] args) {
        new Greeting() {
            @Override
            public void hello() {
                System.out.println("Hello");
            }
        }.hello();
        Greeting.show();
    }
}