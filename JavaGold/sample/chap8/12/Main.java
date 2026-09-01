import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
        try {
            Number parsed = fmt.parse("$100.55");
            System.out.println("Parsed value: " + parsed);
        } catch (ParseException e) { e.printStackTrace(); }
    }
}