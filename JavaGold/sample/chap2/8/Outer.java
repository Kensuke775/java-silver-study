@FunctionalInterface
interface Greeting { void hello(); }
public class Outer {
    private int x = 10;
    public void method() {
        int y = 20;
        Greeting lambda = () -> {
            int z = 30;
         // y++;
            System.out.println("x: " + x);
            System.out.println("y: " + y);
            System.out.println("z: " + z);
        };
        lambda.hello();
     // y++;
    }
    public static void main(String[] args) {
        new Outer().method();
    }
}