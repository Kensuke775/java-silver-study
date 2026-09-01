import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost:1527/library";
        String sql = "SELECT * FROM BOOK";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                System.out.printf("%d:", rs.getInt(1));
                System.out.printf("%-20s", rs.getString(2));
                System.out.printf("%-10s", rs.getString(3));
                System.out.printf("%,d%n", rs.getInt(4));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}