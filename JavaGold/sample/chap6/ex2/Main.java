import java.io.*;

public class Main {
    public static void main(String... args) {
        String file = "note.txt";
        try ( /* insert code here */ ) {
            String text;
            while ((text = reader.readLine()) != null) {
                System.out.println(text);
            }
        } catch(IOException e) { e.printStackTrace();}
    }
}