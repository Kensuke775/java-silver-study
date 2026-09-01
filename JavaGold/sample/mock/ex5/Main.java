import java.sql.*;

public class Main {
    public static void main(String... args) {
        String sql = "SELECT NAME FROM PRODUCT";
        try (Connection con = /* [    (1)    ] */ ;
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}