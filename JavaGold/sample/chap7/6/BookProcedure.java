package proc;
import java.sql.*;

public class BookProcedure {
    public static void countBooksByPrice(
            int maxPrice, int[] bookCount) throws SQLException {
        Connection conn =
            DriverManager.getConnection("jdbc:default:connection");
        PreparedStatement pstmt = conn.prepareStatement(
            "SELECT COUNT(*) FROM BOOK WHERE PRICE <= ?");
        pstmt.setInt(1, maxPrice);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) { bookCount[0] = rs.getInt(1); }
    }
}