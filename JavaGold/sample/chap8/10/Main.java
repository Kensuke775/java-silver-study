import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class Main {
    public static void main(String[] args) {
        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy”NMMŒŽdd“ú");
        TemporalAccessor parsed = fmt.parse("2025”N09ŒŽ30“ú");
        LocalDate date1 = LocalDate.from(parsed);
        System.out.println("Parsed date1: " + date1);
        LocalDate date2 = LocalDate.parse("2025”N09ŒŽ30“ú", fmt);
        System.out.println("Parsed date2: " + date2);
    }
}