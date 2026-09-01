import java.sql.*;

public class Main {
    public static void main(String... args) {
        String sql = "INSERT INTO PRODUCT VALUES(?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/product", "app", "app");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, 5);
            pstmt.setString(2, "Tablet");
            pstmt.setString(3, "Electronics");
            pstmt.setInt(4, 60000);
            pstmt.executeUpdate();
            pstmt.setInt(1, 6);
            pstmt.setNull(4, Types.INTEGER);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}