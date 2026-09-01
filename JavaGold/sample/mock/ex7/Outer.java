public class Outer {
    private String name = "Duke ";
    static final String LANGUAGE = "Java";
    private class Inner{
        void printName() {
            name = "James ";                    // (A)
            System.out.print(name);             // (B)
            System.out.println(LANGUAGE);       // (C)
        }
    }
    public static void main(String[] args) {
        var obj = new Outer();
        obj.new Inner().printName();            // (D)
    }
}