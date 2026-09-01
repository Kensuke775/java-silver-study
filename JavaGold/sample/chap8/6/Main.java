import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
     // Locale.setDefault(new Locale("fr", "FR"));
        Locale[] locales = {
                new Locale("en", "US"),
                new Locale("en"),
                Locale.getDefault()
        };
        for (Locale locale : locales) {
            System.out.println("Locale (Object) : " + locale);
            ResourceBundle bundle = ResourceBundle
                    .getBundle("prop.Message", locale);
            System.out.println("Locale (Bundle) : " + bundle.getLocale());
            System.out.println("file.name     : "
                                + bundle.getString("file.name"));
            System.out.println("---------------");
        }
    }
}