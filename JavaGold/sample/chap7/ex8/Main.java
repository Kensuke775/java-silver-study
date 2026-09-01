import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String... args) {
        String url = "jdbc:derby://localhost:1527/travel";
        String sql = "UPDATE GUEST SET FIRSTNAME = ? " +
                "WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeQuery();                       // (A)
            pstmt.setString(1, "Amy");
            pstmt.setInt(2, 1);
            System.out.println("UPDATE: " + pstmt.executeUpdate());
        } catch (SQLException e) { e.printStackTrace(); }
    }
}