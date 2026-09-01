package impl;
import api.Message;

public class EmailMessage implements Message {
    public void send(String content) {
        System.out.println("Email: " + content);
    }
}