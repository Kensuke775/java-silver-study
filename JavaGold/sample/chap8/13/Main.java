import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        String pattern = "Hello {0}, your total is: {1}";
        String customer = "Duke";
        double total = 50.55;
        NumberFormat currencyFmt =
                        NumberFormat.getCurrencyInstance(Locale.US);
        String message = MessageFormat.format(
                        pattern, customer, currencyFmt.format(total));
        System.out.println(message);
    }
}