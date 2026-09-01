import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        String withValue = "value for Optional", noValue = null;
        Optional<String> opt1 = Optional.of(withValue);       // ’l‚ ‚è
        Optional<String> opt2 = Optional.empty();             // ‹ó
        Optional<String> opt3 = Optional.ofNullable(noValue); // ‹ó
     // Optional<String> optNg = Optional.of(noValue);

        System.out.println("opt3.isEmpty(): " + opt3.isEmpty());
        if(opt1.isPresent())
            System.out.println("opt1.get()    : " + opt1.get());
     // String ng1 = opt2.orElseThrow();
     // String ng2 = opt2.orElseThrow(RuntimeException::new)
        opt1.ifPresent(System.out::println);
        opt3.ifPresentOrElse(System.out::println,
                () -> System.out.println("run"));
        System.out.println(
                opt2.or(() -> Optional.ofNullable(noValue))
                .orElse("other"));
        System.out.println(
                opt2.orElseGet(() -> "supplier"));
    }
}