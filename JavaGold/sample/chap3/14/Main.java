import java.util.Arrays;
import java.util.List;

record Clothes(String name, int size) {}
public class Main {
	public static void main(String[] args) {
        List<Clothes> clothes = List.of(
                new Clothes("Jacket", 1), new Clothes("Shirt", 1));
        List<String> names =
                clothes.stream().map(c -> c.name()).toList();
     // names.add("T-shirt");
        int[] sizes =
                clothes.stream().mapToInt(Clothes::size).toArray();
        Object[] aryObj = clothes.stream().toArray();
        String[] aryStr = clothes.stream()
                .map(Clothes::name).toArray(String[]::new);
        System.out.println("names : " + names);
        System.out.println("sizes : " + Arrays.toString(sizes));
        System.out.println("aryObj: " + Arrays.toString(aryObj));
        System.out.println("aryStr: " + Arrays.toString(aryStr));
	}
}