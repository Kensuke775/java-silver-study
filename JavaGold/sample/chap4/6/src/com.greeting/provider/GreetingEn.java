package provider;
import spi.Greeting;
public class GreetingEn implements Greeting {
    @Override
    public String hello() { return "Hello!"; }
}