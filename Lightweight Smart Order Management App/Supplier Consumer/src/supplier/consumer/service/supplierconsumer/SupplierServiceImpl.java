package supplier.consumer.service.supplierconsumer;

import java.util.List;
import java.util.Scanner;

import supplier.producer.service.supplierproducer.Supplier;
import supplier.producer.service.supplierproducer.SupplierProducer;

public class SupplierServiceImpl implements SupplierService {

    private final SupplierProducer supplierProducer;

    public SupplierServiceImpl(SupplierProducer supplierProducer) {
        this.supplierProducer = supplierProducer;
    }

    @Override
    public void startSupplierService() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Supplier Management Console ===\n");
            System.out.println("1. Add Supplier");
            System.out.println("2. View All Suppliers");
            System.out.println("3. Edit a Supplier");
            System.out.println("4. Delete a Supplier");
            System.out.println("0. Exit\n");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    saveSupplier(sc);
                    break;
                case 2:
                    listAllSuppliers();
                    break;
                case 3:
                    updateSupplier(sc);
                    break;
                case 4:
                    deleteSupplier(sc);
                    break;
                case 0:
                    System.out.println("Exiting Supplier Service...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.\n");
            }
        }
    }

    @Override
    public void saveSupplier(Scanner sc) {
        System.out.print("\nEnter supplier name: ");
        String name = sc.nextLine();

        System.out.print("Enter specializing category: ");
        String category = sc.nextLine();

        System.out.print("Enter contact number: ");
        String contactNumber = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter location: ");
        String location = sc.nextLine();

        System.out.print("Enter description (optional): ");
        String description = sc.nextLine();

        Supplier supplier = new Supplier(0, name, category, contactNumber, email, location, description);
        supplierProducer.saveToDB(supplier);
        System.out.println("Supplier added successfully!");
    }

    @Override
    public void listAllSuppliers() {
        List<Supplier> suppliers = supplierProducer.getAllSuppliers();

        if (suppliers.isEmpty()) {
            System.out.println("\nNo suppliers available.\n");
        } else {
            System.out.printf("\n%-5s | %-20s | %-20s | %-15s | %-25s | %-20s | %-20s\n", 
                    "ID", "Name", "Category", "Contact", "Email", "Location", "Description");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for (Supplier supplier : suppliers) {
                System.out.printf("%-5d | %-20s | %-20s | %-15s | %-25s | %-20s | %-20s\n", 
                        supplier.getId(), supplier.getName(), supplier.getSpecializingCategory(),
                        supplier.getContactNumber(), supplier.getEmail(), supplier.getLocation(),
                        supplier.getDescription());
            }
        }
    }

    @Override
    public void updateSupplier(Scanner sc) {
        System.out.print("\nEnter Supplier Name to update: ");
        String name = sc.nextLine();

        List<Supplier> matchingSuppliers = supplierProducer.getSuppliersByName(name);
        if (matchingSuppliers.isEmpty()) {
            System.out.println("No suppliers found with that name.");
            return;
        }

        if (matchingSuppliers.size() > 1) {
            System.out.println("\nMultiple suppliers found. Please select the correct one:");
            displaySuppliers(matchingSuppliers);
            System.out.print("\nEnter the Supplier ID to update: ");
        } else {
            System.out.println("\nFound one matching supplier:");
            Supplier supplier = matchingSuppliers.get(0);
            displaySupplierDetails(supplier);
     
            System.out.print("\nEnter the Supplier ID to confirm update: ");
        }

        int id = sc.nextInt();
        sc.nextLine();

        Supplier existingSupplier = supplierProducer.getSupplier(id);
        if (existingSupplier == null) {
            System.out.println("Invalid Supplier ID.");
            return;
        }

        
        System.out.print("Enter new Name (leave blank to keep current): ");
        String newName = sc.nextLine();
        
        System.out.print("Enter new Specializing Category (leave blank to keep current): ");
        String newCategory = sc.nextLine();

        System.out.print("Enter new Contact Number (leave blank to keep current): ");
        String newContact = sc.nextLine();

        System.out.print("Enter new Email (leave blank to keep current): ");
        String newEmail = sc.nextLine();

        System.out.print("Enter new Location (leave blank to keep current): ");
        String newLocation = sc.nextLine();

        System.out.print("Enter new Description (leave blank to keep current): ");
        String newDescription = sc.nextLine();

        
        System.out.print("\nAre you sure you want to update this supplier? (y/n): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            supplierProducer.updateSupplier(id, newName, newCategory, newContact, newEmail, newLocation, newDescription);
            System.out.println("Supplier updated successfully!\n");
        } else {
            System.out.println("Supplier was not updated.\n");
        }
    }

    @Override
    public void deleteSupplier(Scanner sc) {
        System.out.print("\nEnter Supplier Name to delete: ");
        String name = sc.nextLine();

        List<Supplier> matchingSuppliers = supplierProducer.getSuppliersByName(name);
        if (matchingSuppliers.isEmpty()) {
            System.out.println("No suppliers found with that name.");
            return;
        }

        if (matchingSuppliers.size() > 1) {
            System.out.println("\nMultiple suppliers found. Please select the correct one:");
            displaySuppliers(matchingSuppliers);
            System.out.print("\nEnter the Supplier ID to delete: ");
        } else {
            System.out.println("\nFound one matching supplier:");
            Supplier supplier = matchingSuppliers.get(0);
            displaySupplierDetails(supplier);
            System.out.print("\nAre you sure you want to delete this supplier? (y/n): ");
            String confirm = sc.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                supplierProducer.deleteSupplier(supplier.getId());
                System.out.println("Supplier deleted successfully!");
            } else {
                System.out.println("Supplier was not deleted.");
            }
            return; 
        }

        // If multiple suppliers, get the ID for deletion
        int id = sc.nextInt();
        sc.nextLine();

        Supplier existingSupplier = supplierProducer.getSupplier(id);
        if (existingSupplier == null) {
            System.out.println("Invalid Supplier ID.");
            return;
        }

        System.out.print("\nAre you sure you want to delete this supplier? (y/n): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            supplierProducer.deleteSupplier(id);
            System.out.println("Supplier deleted successfully!");
        } else {
            System.out.println("Supplier was not deleted.");
        }
    }

    // Helper methods to display supplier details
    private void displaySuppliers(List<Supplier> suppliers) {
        System.out.printf("\n%-5s | %-20s | %-20s | %-15s | %-25s | %-20s | %-20s\n", 
                "ID", "Name", "Category", "Contact", "Email", "Location", "Description");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Supplier supplier : suppliers) {
            System.out.printf("%-5d | %-20s | %-20s | %-15s | %-25s | %-20s | %-20s\n", 
                    supplier.getId(), supplier.getName(), supplier.getSpecializingCategory(),
                    supplier.getContactNumber(), supplier.getEmail(), supplier.getLocation(),
                    supplier.getDescription());
        }
    }

    private void displaySupplierDetails(Supplier supplier) {
        System.out.printf("\n%-5s | %-20s | %-20s | %-15s | %-25s | %-20s | %-20s\n", 
                "ID", "Name", "Category", "Contact", "Email", "Location", "Description");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5d | %-20s | %-20s | %-15s | %-25s | %-20s | %-20s\n", 
                supplier.getId(), supplier.getName(), supplier.getSpecializingCategory(),
                supplier.getContactNumber(), supplier.getEmail(), supplier.getLocation(),
                supplier.getDescription());
    }

}
