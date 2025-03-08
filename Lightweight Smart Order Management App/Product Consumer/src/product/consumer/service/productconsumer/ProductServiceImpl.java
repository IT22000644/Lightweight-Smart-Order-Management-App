package product.consumer.service.productconsumer;

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
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
            case 1:
                // Prompt for product details
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
                        productPrice = Double.parseDouble(sc.nextLine()); // Read the price as string and convert to double
                        validPrice = true; // If the conversion is successful, exit the loop
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for price. Please enter a valid number.");
                    }
                }

                // Create Product object
                Product product = new Product(0, productName, productCategory, productDescription, productPrice);

                // Save product to DB
                productProducer.saveToDB(product);
                System.out.println("-> Product saved successfully!");
                break;

            case 0:
                System.out.println("Exiting Product Service...");
                return;

            default:
                System.out.println("Invalid choice, please try again.");
        }
    }
    }
}
