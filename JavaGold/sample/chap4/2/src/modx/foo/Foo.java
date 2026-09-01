package foo;
import bar.Bar;
public class Foo {
    public static void foo() {
        System.out.println("modx/foo.Foo!");
        System.out.print("  call bar.Bar... ");
        Bar.bar();
    }
}