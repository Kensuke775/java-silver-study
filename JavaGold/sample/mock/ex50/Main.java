import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost:1527/product";
        try (Connection con = DriverManager.getConnection(url);
             CallableStatement cstmt = /* [    (1)    ] */ ) {
            cstmt.setInt(1, 50);
            cstmt.setString(2, "Furniture");
            cstmt.execute();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}