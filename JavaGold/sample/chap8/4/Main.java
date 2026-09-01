import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        dispMessage(new Locale("en", "US"));
        dispMessage(new Locale("fr"));
        dispMessage(new Locale("ja"));
    }
    private static void dispMessage(Locale locale) {
        ResourceBundle bundle =
                ResourceBundle.getBundle("prop.Message", locale);
        System.out.println("Locale       : " + locale);
        System.out.println("home.title   : "
                            + bundle.getString("home.title"));
        System.out.println("menu.greeting: "
                            + bundle.getString("menu.greeting"));
        System.out.println("menu.exit    : "
                            + bundle.getString("menu.exit"));
        System.out.println("---------------");
    }
}