import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost:1527/library";
        String procCall = "{ CALL COUNT_BOOKS_BY_PRICE(?, ?) }";
        try (Connection conn = DriverManager.getConnection(url);
             CallableStatement cstmt = conn.prepareCall(procCall)) {
            cstmt.setInt(1, 4000);
            cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
            cstmt.execute();
            int count = cstmt.getInt(2);
            System.out.println("Books priced <= 4000: " + count);
        } catch (SQLException e) { e.printStackTrace(); }
    }
}