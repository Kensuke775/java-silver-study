import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<String> publisher =
                                    new SubmissionPublisher<>();
        Flow.Subscriber<String> subscriber1 = new MySubscriber();
        Flow.Subscriber<String> subscriber2 = new MySubscriber();
        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);

        publisher.submit("Flow");
        publisher.submit("API");
        publisher.close();
        Thread.sleep(1000L);
    }
}
class MySubscriber implements Flow.Subscriber<String> {
    private Flow.Subscription subscription;
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }
    @Override
    public void onNext(String item) {
        System.out.println("Received: " + item);
        subscription.request(1);
    }
    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }
    @Override
    public void onComplete() {
        System.out.println("Done!");
    }
}