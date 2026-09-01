import java.util.ResourceBundle;

public class Main {
    public static void main(String... args) {
        ResourceBundle bundle =
                ResourceBundle.getBundle("Messages");
        for (String key : bundle.keySet()) {
            System.out.println(key + " " + bundle.getString(key));
        }
    }
}