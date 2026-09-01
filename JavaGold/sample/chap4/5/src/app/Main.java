package app;
import java.util.ServiceLoader;
import spi.Greeting;
public class Main {
    public static void main(String[] args) {
        ServiceLoader<Greeting> loader = 
                            ServiceLoader.load(Greeting.class);
        for (Greeting obj : loader) {
            System.out.println(obj.hello());
        }
    }
}