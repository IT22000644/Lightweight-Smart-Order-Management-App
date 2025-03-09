package product.producer.service.productproducer;

import java.util.List;

public interface ProductProducer {
    void saveToDB(Product product);
    List<Product> getAllProducts();
    void updateProduct(int id, String name, String category, String description, Double price);
    void deleteProduct(int id);
}
