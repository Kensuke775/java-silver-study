import java.text.*;
import java.util.*;

public class Main {
    public static void main(String... args) {
        Locale.setDefault(Locale.US);
        String name = "Duke";
        int amount = 100;
        NumberFormat currencyFmt =
                NumberFormat.getCurrencyInstance();
        ResourceBundle bundle =
                ResourceBundle.getBundle(
                        "prop.Payment", Locale.JAPAN);
        String message =
                MessageFormat.format(bundle.getString("payment"),
                                name, currencyFmt.format(amount));
        System.out.println(message);
    }
}