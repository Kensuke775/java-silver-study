import java.util.function.*;

public class Main {
    public static void main(String... args) {
        UnaryOperator<String> upperFormatter = String::toUpperCase;
        UnaryOperator<String> lowerFormatter = String::toLowerCase;
        Predicate<String> isShortName = day -> day.length() <= 3;

        String day = "Fri";
        String formattedDay = isShortName.test(day)
                                ? upperFormatter.apply(day)
                                : lowerFormatter.apply(day);
        System.out.println(formattedDay);
    }
}