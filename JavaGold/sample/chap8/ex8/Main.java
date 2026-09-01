import java.util.*;

public class Main {
    public static void main(String... args) {
        ResourceBundle bundle =
                ResourceBundle.getBundle("Messages", Locale.US);
        System.out.println("Locale: " + bundle.getLocale());
        System.out.println(bundle.getString("user"));
    }
}