package product.consumer.service.productconsumer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import product.producer.service.productproducer.*;
import supplier.producer.service.supplierproducer.*;

public class ProductServiceImpl implements ProductService {

    private final ProductProducer productProducer;
    private final SupplierProducer supplierProducer;

    public ProductServiceImpl(ProductProducer productProducer, SupplierProducer supplierProducer ) {
        this.productProducer = productProducer;
        this.supplierProducer = supplierProducer;
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

        String supplierName = "";
        while (supplierName.trim().isEmpty()) {
            System.out.print("Enter supplier name (or type 'exit' to cancel): ");
            supplierName = sc.nextLine().trim();

            if (supplierName.equalsIgnoreCase("exit")) {
                System.out.println("Product addition cancelled.");
                return; 
            }

            if (supplierName.trim().isEmpty()) {
                System.out.println("Supplier name cannot be empty. Please try again.");
            }

            // Check if the supplier exists
            List<Supplier> suppliers = supplierProducer.getAllSuppliers(); 
            boolean supplierExists = false;

            for (Supplier supplier : suppliers) {
                if (supplier.getName().equalsIgnoreCase(supplierName)) {
                    supplierExists = true;
                    break;
                }
            }

            if (!supplierExists) {
                System.out.println("No suppliers found with the name: " + supplierName);
                supplierName = "";
            }
        }

        Supplier supplier = new Supplier(0, supplierName);  

        Product product = new Product(0, productName, productCategory, productDescription, productPrice, supplier);

        productProducer.saveToDB(product);
        System.out.println("-> Product saved successfully!");
    }

    @Override
    public void listAllProducts() {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = productProducer.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("No products available.\n");
        } else {
            
            System.out.printf("%-5s | %-20s | %-15s | %-20s | %-10s | %-20s\n", 
                              "ID", "Name", "Category", "Description", "Price ($)", "Supplier");
            System.out.println("------------------------------------------------------------------------------------------------");

            
            for (Product product : products) {
                System.out.printf("%-5d | %-20s | %-15s | %-20s | %-10.2f | %-20s\n",
                                  product.getId(), 
                                  product.getName(), 
                                  product.getCategory(),
                                  product.getDescription(), 
                                  product.getPrice(),
                                  product.getSupplier().getName()); 
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
        
        // Validate price range inputs
        try {
            System.out.print("Enter minimum price (or press Enter to skip): ");
            String minInput = scanner.nextLine().trim();
            if (!minInput.isEmpty()) minPrice = Double.parseDouble(minInput);
            
            System.out.print("Enter maximum price (or press Enter to skip): ");
            String maxInput = scanner.nextLine().trim();
            if (!maxInput.isEmpty()) maxPrice = Double.parseDouble(maxInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid price input! Please enter valid numeric values.");
            return;  
        }
        
    
        System.out.print("Enter supplier name to filter (or press Enter to skip): ");
        String supplierName = scanner.nextLine().trim();

        // Fetch filtered products from the database
        List<Product> filteredProducts = productProducer.filterProducts(name, category, minPrice, maxPrice, supplierName);

        if (filteredProducts.isEmpty()) {
            System.out.println("\nNo matching products found.");
        } else {
            System.out.println("\n=== Filtered Products ===\n");
            System.out.printf("%-5s | %-20s | %-15s | %-20s | %-10s | %-20s\n", 
                    "ID", "Name", "Category", "Description", "Price ($)", "Supplier");
            System.out.println("----------------------------------------------------------------------------------------------");
            for (Product product : filteredProducts) {
            	 System.out.printf("%-5d | %-20s | %-15s | %-20s | %-10.2f | %-20s\n",
                         product.getId(), 
                         product.getName(), 
                         product.getCategory(),
                         product.getDescription(), 
                         product.getPrice(),
                         product.getSupplier().getName()); 
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
            System.out.println("\nTotal Number of Products: " + summary.getTotalProducts());
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

            System.out.printf("%-5s | %-20s | %-15s | %-20s | %-10s | %-20s\n", 
                    "ID", "Name", "Category", "Description", "Price ($)", "Supplier");
            System.out.println("----------------------------------------------------------------------------------------------");

            for (Product product : productList) {
            	System.out.printf("%-5d | %-20s | %-15s | %-20s | %-10.2f | %-20s\n",
                        product.getId(), 
                        product.getName(), 
                        product.getCategory(),
                        product.getDescription(), 
                        product.getPrice(),
                        product.getSupplier().getName()); 
            }

            System.out.print("\nEnter the Product ID to edit: ");
            productId = scanner.nextInt();
            scanner.nextLine();
        } else {
            productId = productList.get(0).getId();
        }

        Product productToUpdate = productProducer.getProduct(productId);

        System.out.print("Enter new name (or press Enter to keep unchanged): ");
        String newName = scanner.nextLine();
        if (newName.trim().isEmpty()) newName = productToUpdate.getName();

        System.out.print("Enter new category (or press Enter to keep unchanged): ");
        String newCategory = scanner.nextLine();
        if (newCategory.trim().isEmpty()) newCategory = productToUpdate.getCategory();

        System.out.print("Enter new description (or press Enter to keep unchanged): ");
        String newDescription = scanner.nextLine();
        if (newDescription.trim().isEmpty()) newDescription = productToUpdate.getDescription();

        System.out.print("Enter new price (or press Enter to keep unchanged): ");
        String priceInput = scanner.nextLine();
        Double newPrice = priceInput.isEmpty() ? productToUpdate.getPrice() : Double.parseDouble(priceInput);

        
        Supplier supplier = productToUpdate.getSupplier();
        System.out.println("\nCurrent supplier: " + supplier.getName());
        System.out.print("\nDo you want to update the supplier? (y/n): ");
        String updateSupplierChoice = scanner.nextLine();

        if (updateSupplierChoice.equalsIgnoreCase("y")) {
            String supplierName = "";
            while (supplierName.trim().isEmpty()) {
                System.out.print("\nEnter supplier name (or type 'exit' to cancel): ");
                supplierName = scanner.nextLine().trim();

                if (supplierName.equalsIgnoreCase("exit")) {
                    System.out.println("Supplier update cancelled.");
                    return;
                }

                // Check if the supplier exists
                List<Supplier> suppliers = supplierProducer.getAllSuppliers();
                boolean supplierExists = false;

                for (Supplier s : suppliers) {
                    if (s.getName().equalsIgnoreCase(supplierName)) {
                        supplier = s;
                        supplierExists = true;
                        break;
                    }
                }

                if (!supplierExists) {
                    System.out.println("No suppliers found with the name: " + supplierName);
                    supplierName = ""; 
                }
            }
        }

        // Update the product with the new details
        productProducer.updateProduct(productId, newName, newCategory, newDescription, newPrice, supplier);
        System.out.println("-> Product updated successfully!");
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

            
            System.out.printf("%-5s | %-20s | %-15s | %-20s | %-10s | %-20s\n", 
                    "ID", "Name", "Category", "Description", "Price ($)", "Supplier");
            System.out.println("----------------------------------------------------------------------------------------------");
            System.out.printf("%-5d | %-20s | %-15s | %-20s | %-10.2f | %-20s\n",
                    product.getId(), 
                    product.getName(), 
                    product.getCategory(),
                    product.getDescription(), 
                    product.getPrice(),
                    product.getSupplier().getName()); 

            
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
        System.out.printf("%-5s | %-20s | %-15s | %-20s | %-10s | %-20s\n", 
                "ID", "Name", "Category", "Description", "Price ($)", "Supplier");
        System.out.println("----------------------------------------------------------------------------------------------");

        for (Product product : matchingProducts) {
        	System.out.printf("%-5d | %-20s | %-15s | %-20s | %-10.2f | %-20s\n",
                    product.getId(), 
                    product.getName(), 
                    product.getCategory(),
                    product.getDescription(), 
                    product.getPrice(),
                    product.getSupplier().getName()); 
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
