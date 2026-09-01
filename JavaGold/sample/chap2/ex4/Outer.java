public class Outer {
    private int x = 30;
    private static int y = 10;
    class Inner {
        private int x = 20;
        private static int y = 30;
        static void stPrint(int y) {
            System.out.println( /* insert code here */ );
        }
    }
    public static void main(String... args) {
        Outer.Inner.stPrint(40);
    }
}