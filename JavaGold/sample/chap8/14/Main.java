import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        Locale[] locales = { new Locale("en", "US"),
                             new Locale("fr", "FR"),
                             new Locale("ja", "JP") };
        for (Locale locale : locales) {
            System.out.println("Locale: " + locale.getLanguage()
                    + "_" + locale.getCountry() + "----------------");
            dispLearningReport(locale);
            System.out.println();
        }
    }
    public static void dispLearningReport(Locale locale) {
        ResourceBundle bundle =
                ResourceBundle.getBundle("prop.Learning", locale);
        String title = MessageFormat.format(
                            bundle.getString("title"), "Duke");
        String report = MessageFormat.format(
                            bundle.getString("report"),
                            LocalDate.of(2025, 9, 30), 5);
        System.out.println(title);
        System.out.println(report);
    }
}