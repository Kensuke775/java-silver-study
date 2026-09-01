public class Main {
    public static void main(String... args) {
        int num = 10;
        MyInterface obj = x -> { return num += x; };    // (A)
        System.out.println(obj.execute(100));           // (B)
    }
}
interface MyInterface { int execute(int data); }