public class Outer {
    void print() { System.out.println("Outer"); }
    class Inner {
        void print() { System.out.println("Inner"); }
    }
    public static void main(String... args) {
        new Outer().method();
    }
    void method() { /* insert code here */ }
}