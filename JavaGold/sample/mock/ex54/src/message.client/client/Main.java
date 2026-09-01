package client;
import api.Message;
import java.util.ServiceLoader;

public class Main {
    public static void main(String... args) {
        ServiceLoader<Message> loader =
                        ServiceLoader.load(Message.class);
        loader.stream()
            .map(ServiceLoader.Provider::get)
            .forEach(obj -> obj.send("Hello!"));
    }
}