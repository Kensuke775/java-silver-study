import java.util.*;

public class Main {
    public static void main(String... args) {
        Locale.setDefault(Locale.FRENCH);
        ResourceBundle bundle =
                ResourceBundle.getBundle("prop.MyResource");
        System.out.println(bundle.getString("message"));
    }
}