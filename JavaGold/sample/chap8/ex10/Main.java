import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main {
    public static void main(String... args) {
        Locale.setDefault(Locale.JAPAN);
        LocalDate date = LocalDate.of(2025, 9, 30);
        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(" /* insert code here */ ");
        System.out.println(date.format(formatter));
    }
}