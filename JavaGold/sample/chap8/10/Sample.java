import java.time.LocalDate;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;

public class Sample {
    public static void main(String[] args) {
        // of()で和暦の日付生成
        JapaneseDate jpDate1 = JapaneseDate.of(2019, 4, 30);
        // from()でLocalDateオブジェクトから和暦日付の生成
        LocalDate localDate = LocalDate.of(2025, 9, 30);
        JapaneseDate jpDate2 = JapaneseDate.from(localDate);
        System.out.println("JapaneseDate ------------------");
        System.out.println("jpDate1: " + jpDate1);
        System.out.println("jpDate2: " + jpDate2);

        // フォーマット指定例
        System.out.println("\nJapaneseDate (Formatted) --------");
        System.out.println(jpDate2.format(DateTimeFormatter
                            .ofPattern("GGGGG yy年MM月dd日 EEEEE")));
        System.out.println(jpDate2.format(DateTimeFormatter
                            .ofPattern("GGGG yy年MM月dd日 EEEE")));
        System.out.println(jpDate2.format(DateTimeFormatter
                            .ofPattern("G y年M月d日 E")));
    }
}