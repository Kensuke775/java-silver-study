import java.text.NumberFormat;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        double number = 1234.50;
        Locale uk = Locale.UK;
        NumberFormat nfDft = NumberFormat.getInstance();
        NumberFormat nfUk = NumberFormat.getInstance(uk);
        NumberFormat intDft = NumberFormat.getIntegerInstance();
        NumberFormat intUk = NumberFormat.getIntegerInstance(uk);
        NumberFormat cryDft = NumberFormat.getCurrencyInstance();
        NumberFormat cryUk = NumberFormat.getCurrencyInstance(uk);

        System.out.println("Default locale ---------");
        System.out.println("Instance: " + nfDft.format(number));
        System.out.println("Integer : " + intDft.format(number));
        System.out.println("Currency: " + cryDft.format(number));
        System.out.println("Locale.UK --------------");
        System.out.println("Instance: " + nfUk.format(number));
        System.out.println("Integer : " + intUk.format(number));
        System.out.println("Currency: " + cryUk.format(number));
    }
}