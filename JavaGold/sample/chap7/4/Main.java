import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost:1527/library";
        String sql = "UPDATE BOOK SET" +
                    " TITLE = ?, AUTHOR = ?, PRICE = ?" +
                    " WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "World Travel Guide");
            pstmt.setObject(2, "Martin", java.sql.Types.VARCHAR);
            pstmt.setNull(3, java.sql.Types.INTEGER);
            pstmt.setInt(4, 5);
            System.out.println("count: " + pstmt.executeUpdate());
        } catch (SQLException e) { e.printStackTrace(); }
    }
}