public class Outer {
    private String text = "A ";
    private static String stText = "stA ";
    private class Inner{
        String text = "B ";
        static String stText = "stB ";
        void print(String text) {
            System.out.print("Text  : " + text);    // 7行目のtext
            System.out.print(this.text);            // 5行目のtext
            System.out.println(Outer.this.text);    // 2行目のtext
        }
        static void stPrint() {
            System.out.print("stText: " + stText);  // 6行目のstText
            System.out.println(Outer.stText);       // 3行目のstText
        }
    }
    public static void main(String[] args) {
        new Outer().new Inner().print("C ");
        Inner.stPrint();
    }
}