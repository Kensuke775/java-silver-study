import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        String[] strArray = {"D", "U", "K", "E"};
        Arrays.sort(strArray, Comparator.naturalOrder());
        System.out.println("sort()    : " + Arrays.toString(strArray));
        System.out.println("strArray  : " + strArray);
        Object[] objArray = {"Java", 17};
     // Arrays.sort(objArray);   // ClassCastException
        int[] numArray = {1, 3, 5};
        int[] small    = {1, 3};
        int[] same     = {1, 3, 5};
        int[] large    = {5, 3, 1};
        System.out.print("compare() : ");
        System.out.print(Arrays.compare(numArray, small)  + " ");
        System.out.print(Arrays.compare(numArray, same)  + " ");
        System.out.print(Arrays.compare(numArray, large)  + "\n");
        System.out.print("mismatch(): ");
        System.out.print(Arrays.mismatch(numArray, small)  + " ");
        System.out.print(Arrays.mismatch(numArray, same));
    }
}