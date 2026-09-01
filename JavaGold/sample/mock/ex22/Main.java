import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String... args) {
        try (Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/product", "app", "app");
             PreparedStatement pstmt = con.prepareStatement(
                    "SELECT ID, NAME, CATEGORY, PRICE FROM PRODUCT"
                    + " WHERE CATEGORY = ?")) {
            // insert code here
            ResultSet rs = pstmt.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product(rs.getInt(1), rs.getString(2),
                                    rs.getString(3), rs.getInt(4));
                products.add(p);
            }
            products.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
record Product(int id, String name, String category, int price) {}