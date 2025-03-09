package product.consumer.service.productconsumer;

import java.util.Scanner;

public interface ProductService {
    void startProductService();
    void listAllProducts();
    void updateProduct();
    void deleteProduct();
    void filterProducts(Scanner scanner);
    void productSummary();
    void saveProduct(Scanner sc);
}