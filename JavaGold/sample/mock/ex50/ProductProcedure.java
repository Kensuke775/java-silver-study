package proc;
import java.sql.*;

public class ProductProcedure {
    public static void discountCategory(int percent, String category) throws SQLException {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Percent: 0-100");
        }
        String sql = "UPDATE PRODUCT SET PRICE = PRICE * ? / 100 WHERE CATEGORY = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:default:connection");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, percent);
            pstmt.setString(2, category);
            pstmt.executeUpdate();
        }
    }
}