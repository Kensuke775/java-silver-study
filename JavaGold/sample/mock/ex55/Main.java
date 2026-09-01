import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String... args) throws Exception {
        SubmissionPublisher<String> publisher
                                     = new SubmissionPublisher<>();
        MySubscriber<String> subscriber1 = new MySubscriber<>();
        MySubscriber<String> subscriber2 = new MySubscriber<>();
        List<String> list = List.of("1", "x", "2", "x", "3");
        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);
        System.out.println("Publishing started");
        list.forEach(s -> publisher.submit(s));
        publisher.close();
        Thread.sleep(1000);
    }
}
class MySubscriber<String> implements Flow.Subscriber<String> {
    private Flow.Subscription subscription;
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }
    @Override
    public void onNext(String item) {
        if(!"x".equals(item)) {
            System.out.println("Received: " + item);
        }
        subscription.request(1);
    }
    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }
    @Override
    public void onComplete() {
        System.out.println("Publishing completed");
    }
}