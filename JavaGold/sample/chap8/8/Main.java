import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2025, 9, 30);
        DateTimeFormatter fmt1 = DateTimeFormatter.BASIC_ISO_DATE;
        System.out.println("BASIC_ISO_DATE: " + fmt1.format(date));
        DateTimeFormatter fmt2 = DateTimeFormatter.ISO_LOCAL_DATE;
        System.out.println("ISO_LOCAL_DATE: " + date.format(fmt2));

        DateTimeFormatter fmtFull =
                DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        DateTimeFormatter fmtShort =
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        System.out.println("\nDefault locale ----------");
        System.out.println("FULL : " + fmtFull.format(date));
        System.out.println("SHORT: " + fmtShort.format(date));
        System.out.println("Locale.US ---------------");
        DateTimeFormatter fmtFullUs = fmtFull.localizedBy(Locale.US);
        System.out.println("FULL : " + fmtFullUs.format(date));
        DateTimeFormatter fmtShortUs = fmtShort.localizedBy(Locale.US);
        System.out.println("SHORT: " + fmtShortUs.format(date));
    }
}