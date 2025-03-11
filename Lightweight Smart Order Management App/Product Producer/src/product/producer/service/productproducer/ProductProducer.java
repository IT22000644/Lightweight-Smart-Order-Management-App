package product.producer.service.productproducer;

import java.util.List;
import java.util.Map;

import supplier.producer.service.supplierproducer.Supplier;

public interface ProductProducer {
    void saveToDB(Product product);
    List<Product> getAllProducts();
    void updateProduct(int id, String name, String category, String description, Double price, Supplier supplier);
    void deleteProduct(int id);
    List<Product> filterProducts(String name, String category, Double minPrice, Double maxPrice, String supplierName);
    ProductSummary getProductSummary();
    double getProductPrice(int productId);
    Product getProduct(int productId);
    Map<String, Integer> getCategoryCount();
}
