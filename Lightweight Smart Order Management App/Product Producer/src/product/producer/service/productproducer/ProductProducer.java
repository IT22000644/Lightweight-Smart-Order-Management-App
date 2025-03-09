package product.producer.service.productproducer;

import java.util.List;

public interface ProductProducer {
    void saveToDB(Product product);
    List<Product> getAllProducts();
}
