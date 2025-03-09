package product.consumer.service.productconsumer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
            System.out.println("\n=== Product Management Console ===\n");
            System.out.println("1. Save Product to Database");
            System.out.println("2. View All Products");
            System.out.println("3. Edit a Product");
            System.out.println("4. Delete a Product");
            System.out.println("0. Exit\n");

            System.out.print("Enter your choice: ");

            int choice = -1; 
            
            while (true) {
                try {
                    choice = sc.nextInt(); 
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number. \n");
                    System.out.print("Enter your choice: ");
                    sc.nextLine(); 
                }
            }

            switch (choice) {
                case 1:
                    saveProduct(sc);
                    break;

                case 2:
                    listAllProducts();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 0:
                    System.out.println("Exiting Product Service...");
                    return;

                default:
                    System.out.println("Invalid choice, please try again.\n");
            }
        }
    }
    

    @Override
    public void saveProduct(Scanner sc) {
        String productName = "";
        String productCategory = "";
        String productDescription = "";

        
        while (productName.trim().isEmpty()) {
            System.out.print("\nEnter product name: ");
            productName = sc.nextLine();
            if (productName.trim().isEmpty()) {
                System.out.println("\nProduct name cannot be empty. Please try again.");
            }
        }

        
        while (productCategory.trim().isEmpty()) {
            System.out.print("Enter product category: ");
            productCategory = sc.nextLine();
            if (productCategory.trim().isEmpty()) {
                System.out.println("\nProduct category cannot be empty. Please try again.");
            }
        }

        
        System.out.print("Enter product description (optional): ");
        productDescription = sc.nextLine();

       
        double productPrice = 0.0;
        boolean validPrice = false;
        while (!validPrice) {
            try {
                System.out.print("Enter product price: ");
                productPrice = Double.parseDouble(sc.nextLine());
                validPrice = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for price. Please enter a valid number.\n");
            }
        }

        
        Product product = new Product(0, productName, productCategory, productDescription, productPrice);

        
        productProducer.saveToDB(product);
        System.out.println("-> Product saved successfully!");
    }

    @Override
    public void listAllProducts() {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = productProducer.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            System.out.printf("%-5s | %-20s | %-15s | %-20s | %-10s\n", "ID", "Name", "Category", "Description", "Price ($)");
            System.out.println("----------------------------------------------------------------------------------------------");
            for (Product product : products) {
                System.out.printf("%-5d | %-20s | %-15s | %-20s | %-10.2f\n",
                        product.getId(), product.getName(), product.getCategory(),
                        product.getDescription(), product.getPrice());
            }
        }

        System.out.println("\n1. Filter Products");
        System.out.println("2. View Product Summary");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            filterProducts(scanner);
        } else if (choice == 2) {
            productSummary();
        }
        
    }

    @Override
    public void filterProducts(Scanner scanner) {
        System.out.print("\nEnter product name to filter (or press Enter to skip): ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter product category to filter (or press Enter to skip): ");
        String category = scanner.nextLine().trim();
        
        Double minPrice = null, maxPrice = null;
        
        System.out.print("Enter minimum price (or press Enter to skip): ");
        String minInput = scanner.nextLine().trim();
        if (!minInput.isEmpty()) minPrice = Double.parseDouble(minInput);
        
        System.out.print("Enter maximum price (or press Enter to skip): ");
        String maxInput = scanner.nextLine().trim();
        if (!maxInput.isEmpty()) maxPrice = Double.parseDouble(maxInput);
        
        List<Product> filteredProducts = productProducer.filterProducts(name, category, minPrice, maxPrice);
        
        if (filteredProducts.isEmpty()) {
            System.out.println("\nNo matching products found.");
        } else {
            System.out.println("\n=== Filtered Products ===\n");
            System.out.printf("%-5s | %-20s | %-15s | %-20s | %-10s\n", "ID", "Name", "Category", "Description", "Price ($)");
            System.out.println("----------------------------------------------------------------------------------------------");
            for (Product product : filteredProducts) {
                System.out.printf("%-5d | %-20s | %-15s | %-20s | %-10.2f\n",
                        product.getId(), product.getName(), product.getCategory(),
                        product.getDescription(), product.getPrice());
            }
        }
        
        
        System.out.println("\n1. Filter Again");
        System.out.println("2. View Product Summary");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            filterProducts(scanner); 
        } else if (choice == 2) {
           productSummary();
        }
    }
    
    @Override
    public void productSummary() {
    	Scanner scanner = new Scanner(System.in);
        List<Product> products = productProducer.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("\nNo products available for summary.");
        } else {
            
            ProductSummary summary = productProducer.getProductSummary(); 

            System.out.println("\n=== Product Summary ===");
            System.out.println("Total Number of Products: " + summary.getTotalProducts());
            System.out.println("Total Number of Categories: " + summary.getTotalCategories());
            System.out.printf("Lowest Product Price: $%.2f\n", summary.getLowestPrice());
            System.out.printf("Highest Product Price: $%.2f\n", summary.getHighestPrice());
            System.out.println("Highest Products in Same Category: " + summary.getHighestSameCategoryCount());
            System.out.println("Lowest Products in Same Category: " + summary.getLowestSameCategoryCount());
            
        }

        startProductService();
    }
    
    @Override
    public void updateProduct() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("\nEnter the product name to search: ");
        String productName = scanner.nextLine();

        
        List<Product> productList = productProducer.getAllProducts()
            .stream()
            .filter(p -> p.getName().equalsIgnoreCase(productName))
            .collect(Collectors.toList());

        if (productList.isEmpty()) {
            System.out.println("\nProduct(s) were not Found.");
            return;
        }

        int productId;
        if (productList.size() > 1) {
            System.out.println("\nMultiple products found:\n");

            
            System.out.printf("%-5s | %-20s | %-15s | %-20s | %-10s\n", "ID", "Name", "Category", "Description", "Price ($)");
            System.out.println("----------------------------------------------------------------------------------------------");

            for (Product product : productList) {
                System.out.printf("%-5d | %-20s | %-15s | %-20s | %-10.2f\n",
                        product.getId(), product.getName(), product.getCategory(),
                        product.getDescription(), product.getPrice());
            }

            System.out.print("\nEnter the Product ID to edit: ");
            productId = scanner.nextInt();
            scanner.nextLine();
        } else {
            productId = productList.get(0).getId();
        }

        
        System.out.print("Enter new name (or press Enter to keep unchanged): ");
        String newName = scanner.nextLine();
        
        System.out.print("Enter new category (or press Enter to keep unchanged): ");
        String newCategory = scanner.nextLine();
        
        System.out.print("Enter new description (or press Enter to keep unchanged): ");
        String newDescription = scanner.nextLine();
        
        System.out.print("Enter new price (or press Enter to keep unchanged): ");
        String priceInput = scanner.nextLine();
        Double newPrice = priceInput.isEmpty() ? null : Double.parseDouble(priceInput);

        productProducer.updateProduct(productId, newName, newCategory, newDescription, newPrice);
        
  
    }
    
    @Override
    public void deleteProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter the product name to search: ");
        String productName = scanner.nextLine();

        List<Product> matchingProducts = productProducer.getAllProducts()
            .stream()
            .filter(p -> p.getName().equalsIgnoreCase(productName))
            .collect(Collectors.toList());

        if (matchingProducts.isEmpty()) {
            System.out.println("\nNo product found.");
            return;
        }

       
        if (matchingProducts.size() == 1) {
            Product product = matchingProducts.get(0);
            System.out.println("\nProduct Found:\n");

            
            System.out.printf("%-5s | %-20s | %-15s | %-30s | %-10s\n", "ID", "Name", "Category", "Description", "Price ($)");
            System.out.println("----------------------------------------------------------------------------------------------");
            System.out.printf("%-5d | %-20s | %-15s | %-30s | %-10.2f\n",
                    product.getId(), product.getName(), product.getCategory(),
                    product.getDescription(), product.getPrice());

            
            System.out.print("\nAre you sure you want to delete this product? (y/n): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("y")) {
                productProducer.deleteProduct(product.getId());
                System.out.println("\nProduct deleted successfully.");
            } else {
                System.out.println("\nProduct was not deleted.");
            }
            return;
        }

       
        System.out.println("\nMultiple products found:\n");
        System.out.printf("%-5s | %-20s | %-15s | %-30s | %-10s\n", "ID", "Name", "Category", "Description", "Price ($)");
        System.out.println("----------------------------------------------------------------------------------------------");

        for (Product product : matchingProducts) {
            System.out.printf("%-5d | %-20s | %-15s | %-30s | %-10.2f\n",
                    product.getId(), product.getName(), product.getCategory(),
                    product.getDescription(), product.getPrice());
        }

        
        System.out.print("\nEnter the Product ID to delete: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); 

     
        System.out.print("\nAre you sure you want to delete this product? (y/n): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y")) {
            productProducer.deleteProduct(productId);
            System.out.println("\nProduct deleted successfully.");
        } else {
            System.out.println("\nProduct was not deleted.");
        }
    }
}
