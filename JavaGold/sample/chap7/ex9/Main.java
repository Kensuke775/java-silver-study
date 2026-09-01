import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String... args) {
        String url = "jdbc:derby://localhost:1527/travel";
        String sql = "DELETE FROM GUEST WHERE ID =  ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 5);
            if(!pstmt.execute()) {
                System.out.println("DELETE: " + /* insert code here */);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}