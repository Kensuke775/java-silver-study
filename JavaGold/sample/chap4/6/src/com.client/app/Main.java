package app;
import java.util.ServiceLoader;
import spi.Greeting;
public class Main {
    public static void main(String[] args) {
        ServiceLoader<Greeting> loader = 
                            ServiceLoader.load(Greeting.class);
        loader.stream()
                .map(ServiceLoader.Provider::get)
                .forEach(obj -> System.out.println(obj.hello()));
    }
}