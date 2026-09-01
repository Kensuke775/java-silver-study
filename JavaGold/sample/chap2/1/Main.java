class Outer {
    class Inner {
        void print() { System.out.println("Inner"); }
    }
    static class Nested {
        static void print() { System.out.println("Nested"); }
    }
    public static void main(String[] args) {
        Outer outer = new Outer();
        Inner inner = outer.new Inner();
        inner = new Outer().new Inner();
        Nested nested = new Outer.Nested();
        nested = new Nested();
    }
}
public class Main {
    public static void main(String[] args) {
        Outer.Inner inner = new Outer().new Inner();
        Outer.Nested nested = new Outer.Nested();

        new Outer().new Inner().print();
        Outer.Nested.print();
    }
}