package product.producer.service.productproducer;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl {
    private List<String> productList = new ArrayList<>();

    public void addProduct(String name, double price) {
        String product = name + " - $" + price;
        productList.add(product);
        System.out.println("Added Product: " + product);
    }

    public List<String> getProducts() {
        return productList;
    }
}