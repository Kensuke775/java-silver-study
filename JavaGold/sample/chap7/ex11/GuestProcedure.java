package proc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestProcedure {
    public static void countGuestsByCountry(
            String targetCountry, int[] guestCount) throws SQLException {
        Connection conn =
            DriverManager.getConnection("jdbc:default:connection");
        PreparedStatement pstmt = conn.prepareStatement(
            "SELECT COUNT(*) FROM GUEST WHERE COUNTRY = ?");
        pstmt.setString(1, targetCountry);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            guestCount[0] = rs.getInt(1);
        }
    }
}