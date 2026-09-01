public class Outer {
    private String x = "x";
    private static String y = "y";
    public static class Nested{
        String v = "v";
        static String w = "w";
        void method() {
         // System.out.println(x);
            System.out.println("method()   : " + y + v + w);
        }
        static void staMethod() {
         // System.out.println(x);
            System.out.println("staMethod(): " + y + w);
         // System.out.println(v);
        }
    }
    public static void main(String[] args) {
        new Outer.Nested().method();
     // Outer.Nested.method();
        Outer.Nested.staMethod();
    }
}