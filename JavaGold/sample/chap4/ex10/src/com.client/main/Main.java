package main;
import api.Product;
import java.util.ServiceLoader;

public class Main {
    public static void main(String... args) {
        ServiceLoader<Product> loader = ServiceLoader.load(Product.class);
        for(var p : loader) p.show();
    }
}