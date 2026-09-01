package impl;
import api.Message;

public class SNSMessage implements Message {
    public void send(String content) {
        System.out.println("SNS: " + content);
    }
}