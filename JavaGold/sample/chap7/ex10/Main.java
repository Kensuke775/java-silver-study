import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String... args) {
        String url = "jdbc:derby://localhost:1527/travel";
        String sql1 = "SELECT FIRSTNAME FROM GUEST";
        String sql2 = "SELECT COUNTRY FROM GUEST";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs1 = stmt.executeQuery(sql1);
             ResultSet rs2 = stmt.executeQuery(sql2);) {
            while (rs1.next()) {
                System.out.println(rs1.getString("FIRSTNAME"));
            }
            while (rs2.next()) {
                System.out.println(rs2.getString("COUNTRY"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}