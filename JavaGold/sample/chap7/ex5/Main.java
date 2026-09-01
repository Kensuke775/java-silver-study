import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String... args) {
        String url = "jdbc:derby://localhost:1527/travel";
        String sql = "SELECT * FROM GUEST WHERE ID = 10";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {                                 // (A)
                System.out.println(rs.getString("FIRSTNAME"));  // (B)
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}