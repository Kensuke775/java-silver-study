import java.time.*;
import java.time.chrono.*;
import java.time.format.*;
import java.util.*;

public class Main {
    public static void main(String... args) {
        Locale.setDefault(new Locale("ja", "JP"));
        LocalDate date = LocalDate.of(2026, 1, 1);
        JapaneseDate jpDate = JapaneseDate.from(date);
        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern("GGGGGyy MM/dd(EEEEE)");
        System.out.println(jpDate.format(dtf));
    }
}