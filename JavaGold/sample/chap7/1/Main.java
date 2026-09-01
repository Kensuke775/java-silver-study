import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost:1527/library;create=true";
        String user = "app";
        String password = "app";
        try (Connection conn =
                 DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected: " + conn);
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
        }
    }
}