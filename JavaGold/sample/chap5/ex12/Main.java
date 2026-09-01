import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String... args) {
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(10000);
                return "call() executed";
            }};
        List<Future<String>> list = new ArrayList<>();
        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            list.add(service.submit(task));
            list.add(service.submit(task));
            service.shutdown();
            for (Future<String> f : list) {
                System.out.println(f.get());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}