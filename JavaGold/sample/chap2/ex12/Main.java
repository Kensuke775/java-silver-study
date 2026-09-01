interface MyInter { int method(String s); }
public class Main {
    public static void main(String... args) {
        MyInter obj = (String s) -> s.length();
        System.out.println(obj.method("Java"));
    }
}