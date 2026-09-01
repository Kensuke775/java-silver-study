import java.time.*;
import java.time.format.*;
import java.util.Locale;

public class Main {
    public static void main(String... args) {
        Locale.setDefault(Locale.JAPAN);
        LocalDate date = LocalDate.of(2021, 9, 14);
        DateTimeFormatter formatter = // insert code here
        System.out.println(date.format(formatter));
    }
}