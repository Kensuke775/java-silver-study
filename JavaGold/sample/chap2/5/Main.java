interface Greeting { void hello(); }
public class Main {
    void sayHello() {
        Greeting english = new Greeting() {
            @Override public void hello() {
                System.out.println("Hello!");
            }
        };
        english.hello();
    }
    static Greeting getGreeting() {
        return new Greeting() {
            @Override public void hello() {
                System.out.println("Bonjour!");
            }
        };
    }
    public static void main(String[] args) {
        new Main().sayHello();
        Greeting french = getGreeting();
        french.hello();
    }
}