import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale usEn = new Locale.Builder()
                .setLanguage("en")
                .setRegion("US")
                .build();
        System.out.println(usEn);
        Locale jaNg = new Locale.Builder()
                .setLanguage("ja")
                .setRegion("JAPAN")
                .build();
        System.out.println(jaNg);
    }
}