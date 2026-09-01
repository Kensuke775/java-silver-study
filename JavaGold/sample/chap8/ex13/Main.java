import java.text.*;
import java.util.Locale;

public class Main {
    public static void main(String... args) {
        Locale.setDefault(Locale.JAPAN);
        String priceStr = "$20,55";                             // (A)
        NumberFormat currencyFmt =
                NumberFormat.getCurrencyInstance();             // (B)
        try {
            var price = currencyFmt.parse(priceStr);            // (C)
            System.out.println(price);
        } catch (ParseException e) { e.printStackTrace(); }
    }
}