import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String... args) {
        String url = "jdbc:derby://localhost:1527/library";
        String sql = "SELECT TITLE, AUTHOR FROM BOOK";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                System.out.print(rs.getString(1) + ", ");      // (A)
                System.out.print(rs.getString(2) + "\n");      // (B)
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}