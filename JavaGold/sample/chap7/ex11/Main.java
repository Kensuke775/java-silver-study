import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String... args) {
        String url = "jdbc:derby://localhost:1527/travel";
        String procCall = "{ CALL COUNT_GUEST_BY_COUNTRY(?, ?) }";
        try (Connection conn = DriverManager.getConnection(url);
             CallableStatement cstmt = conn.prepareCall(procCall)) {
        /*
            cstmt.[    (1)    ];
            cstmt.[    (2)    ];
         */
            cstmt.execute();
            var count = cstmt.getInt(2);
            System.out.println("Number of guests: " + count);
        } catch (SQLException e) { e.printStackTrace(); }
    }
}