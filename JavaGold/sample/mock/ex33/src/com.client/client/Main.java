package client;
import service.MessageService;

public class Main {
    public static void main(String... args) {
        System.out.println(new MessageService().getMessage());
    }
}