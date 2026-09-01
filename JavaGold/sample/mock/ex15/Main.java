import java.text.*;
import java.util.*;

public class Main {
    public static void main(String... args) {
        double price = 5500.00;
        Locale.setDefault(new Locale("en", "US"));
        NumberFormat fmt
                = NumberFormat.getCurrencyInstance(Locale.JAPAN);
        System.out.printf("Price: " + fmt.format(price));
    }
}