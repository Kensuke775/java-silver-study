import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class Main {
    public static void main(String... args) {
        Locale.setDefault(Locale.JAPAN);
        var number = 1234.15;
        NumberFormat nfmt = // insert code here
        System.out.println(nfmt.format(number));
    }
}