import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale dft1 = Locale.getDefault();
        System.out.println("after getDefault(): " + dft1);
        Locale.setDefault(Locale.FRANCE);
        Locale dft2 = Locale.getDefault();
        System.out.println("after setDefault(): " + dft2);
    }
}