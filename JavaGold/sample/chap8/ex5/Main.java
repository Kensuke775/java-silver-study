import java.util.*;

public class Main {
    public static void main(String... args) {
        Locale.setDefault(new Locale("ja", "JP"));
        ResourceBundle bundle =
                ResourceBundle.getBundle("prop.MyResource");
        System.out.println("message: " + bundle.getString("message"));
    }
}