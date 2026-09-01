public class Main {
    public static void main(String... args) {
        // insert code here
    }
}
class Outer {
    private static final String NAME = "Duke";
    class Inner {
        void print() { System.out.println(NAME); }
    }
    static class Nested{
        void print() { System.out.println(NAME); }
    }
}