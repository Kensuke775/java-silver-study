import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost:1527/library";
        String sql = "SELECT ID, TITLE FROM BOOK WHERE ID = ?";
     // String sql = "DELETE FROM BOOK WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 5);
            if (pstmt.execute()) {
                try(ResultSet rs = pstmt.getResultSet();) {
                    if (rs.next()) {
                        System.out.println(rs.getString(2));
                    }
                }
            } else {
                System.out.println("count: " + pstmt.getUpdateCount());
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}