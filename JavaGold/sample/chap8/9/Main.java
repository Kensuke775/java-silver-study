import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2025, 9, 30);
        DateTimeFormatter fmtJP =
            DateTimeFormatter.ofPattern("Gy”N MŒŽd“ú EEEE");
        DateTimeFormatter fmtUS =
            DateTimeFormatter.ofPattern("Gy MMMM d, EEEE", Locale.US);
        System.out.println("Default locale: " + date.format(fmtJP));
        System.out.println("Locale.US     : " + date.format(fmtUS));
    }
}