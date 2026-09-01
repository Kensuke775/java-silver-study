import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale ja = new Locale("ja");
        Locale jaJp = new Locale("ja", "JP");
        Locale jpNg = new Locale("JAPAN");
        System.out.println(ja);
        System.out.println(jaJp);
        System.out.println(jpNg);

        Locale enGb = new Locale("en", "GB");
        System.out.printf("%-15s: %s, %s%n", "enGb (Code)",
                enGb.getLanguage(), enGb.getCountry());
        System.out.printf("%-15s: %s, %s%n", "enGb (Default)",
                enGb.getDisplayLanguage(),
                enGb.getDisplayCountry());
        System.out.printf("%-15s: %s, %s%n", "enGb (fr)",
                enGb.getDisplayLanguage(Locale.FRENCH),
                enGb.getDisplayCountry(Locale.FRANCE));
    }
}