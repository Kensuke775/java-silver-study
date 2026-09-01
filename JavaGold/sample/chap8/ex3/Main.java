import java.util.Locale;

public class Main {
    public static void main(String... args) {
        Locale.setDefault(new Locale("ja"));                    // (A)
        Locale locale = new Locale.Builder()
                .setLanguage("en")                              // (B)
                .setRegion("USA")                               // (C)
                .build();
        System.out.print(locale.getDisplayLanguage() + ", ");
        System.out.print(locale.getDisplayCountry());
    }
}