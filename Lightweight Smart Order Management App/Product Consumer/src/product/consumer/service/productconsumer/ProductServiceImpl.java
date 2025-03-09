package product.consumer.service.productconsumer;

import java.util.List;
import java.util.Scanner;
import product.producer.service.productproducer.*;

public class ProductServiceImpl implements ProductService {

    private final ProductProducer productProducer;

    public ProductServiceImpl(ProductProducer productProducer) {
        this.productProducer = productProducer;
    }

    @Override
    public void startProductService() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Product Management Console ===");
            System.out.println("1. Save Product to Database");
            System.out.println("2. View All Products");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();  

            switch (choice) {
                case 1:
                    saveProduct(sc);
                    break;

                case 2:
                    listAllProducts();
                    break;

                case 0:
                    System.out.println("Exiting Product Service...");
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void saveProduct(Scanner sc) {
        System.out.print("Enter product name: ");
        String productName = sc.nextLine();

        System.out.print("Enter product category: ");
        String productCategory = sc.nextLine();

        System.out.print("Enter product description: ");
        String productDescription = sc.nextLine();

        double productPrice = 0.0;
        boolean validPrice = false;
        while (!validPrice) {
            try {
                System.out.print("Enter product price: ");
                productPrice = Double.parseDouble(sc.nextLine()); 
                validPrice = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for price. Please enter a valid number.");
            }
        }

        Product product = new Product(0, productName, productCategory, productDescription, productPrice);
        productProducer.saveToDB(product);
        System.out.println("-> Product saved successfully!");
    }

    @Override
    public void listAllProducts() {
        System.out.println("\n=== List of All Products ===");
        List<Product> products = productProducer.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product product : products) {
                System.out.println("ID: " + product.getId() +
                                   " | Name: " + product.getName() +
                                   " | Category: " + product.getCategory() +
                                   " | Description: " + product.getDescription() +
                                   " | Price: $" + product.getPrice());
            }
        }
    }
}
