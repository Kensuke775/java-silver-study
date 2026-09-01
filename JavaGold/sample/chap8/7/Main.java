import java.time.LocalDate;
import java.time.Month;

public class Main {
    public static void main(String[] args) {
        LocalDate base = LocalDate.of(2025, Month.SEPTEMBER, 30);
        System.out.print("base      : " + base.getDayOfWeek() + ", ");
        System.out.print(base.getMonth() + " ");
        System.out.print(base.getDayOfMonth() + ", ");
        System.out.println(base.getYear());

        LocalDate now = LocalDate.now();
        LocalDate update = now.plusYears(2)
                .plusMonths(1)
                .minusDays(10);
        System.out.println("now       : " + now);
        System.out.println("now-update: " + update);
    }
}