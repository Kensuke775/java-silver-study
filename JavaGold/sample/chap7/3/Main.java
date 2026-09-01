import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost:1527/library";
        String sql = "INSERT INTO BOOK VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 5);
            pstmt.setString(2, "Travel Guide for Children");
            pstmt.setString(3, "Anderson");
            pstmt.setInt(4, 2000);
         // pstmt.setInt(5, 2000);
            int count = pstmt.executeUpdate();
            System.out.println("count: " + count);
        } catch (SQLException e) { e.printStackTrace(); }
    }
}