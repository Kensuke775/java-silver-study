import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        Locale[] locales = { Locale.ENGLISH, Locale.FRANCE };
        for (Locale locale : locales) {
            ResourceBundle bundle = ResourceBundle
                        .getBundle("prop.SimpleMessage", locale);
            System.out.println("Locale: " + bundle.getLocale());
            for (String key : bundle.keySet()) {
                System.out.printf(
                        "%-6s: %-3s%n", key, bundle.getString(key));
            }
            System.out.println("---------------");
        }
    }
}