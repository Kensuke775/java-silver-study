public class Main {
    private int mValue = 1;
    public void method(final int num1) {
        int num2 = 200;
        final class Local {
            public static int lValue = 10;
            void print() {
                System.out.println("mValue: " + mValue);
                System.out.println("lValue: " + lValue);
                System.out.println("num1  : " + num1);
                System.out.println("num2  : " + num2);
                mValue = 0; lValue = 0;
             // num2 = 0;
            }
        }
        new Local().print();
    }
    public static void main(String[] args) {
        new Main().method(100);
    }
}