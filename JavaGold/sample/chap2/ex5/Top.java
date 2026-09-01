public class Top {
    int a, b;
    public void method(int a) {
        class Local {
            void print() {
                int b;                              // (A)
                System.out.print(Local.num + ":");  // (B)
                System.out.print(a + ":");          // (C)
                System.out.print(b);                // (D)
            }
            public static int num = 10;             // (E)
        }
        new Local().print();
    }
}