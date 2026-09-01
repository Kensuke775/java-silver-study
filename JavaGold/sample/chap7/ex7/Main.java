import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String... args) {
        String url = "jdbc:derby://localhost:1527/library";
        String sql = "INSERT INTO BOOK VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 10);
            pstmt.setString(2, "Macbeth");
            pstmt.setString(3, "Shakespeare");
            System.out.println("INSERT: " + pstmt.executeUpdate());
        } catch (SQLException e) { e.printStackTrace(); }
    }
}