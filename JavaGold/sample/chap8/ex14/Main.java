import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class Main {
    public static void main(String... args) {
        String pattern = "Hello {0}, your appointment is on {1}.";
        String name = "Duke";
        LocalDate date = LocalDate.of(2025, 9, 30);
        Locale.setDefault(Locale.US);
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        String message = // insert code here
        System.out.println(message);
    }
}

