package provider;
import spi.Greeting;
public class GreetingFr implements Greeting {
    @Override
    public String hello() { return "Bonjour!"; }
}