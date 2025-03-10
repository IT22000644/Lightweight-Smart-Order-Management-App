package inventory.consumer.service.inventoryconsumer;

import java.util.List;
import java.util.Scanner;

import inventory.producer.service.inventoryproducer.Inventory;
import inventory.producer.service.inventoryproducer.InventoryProducer;
import product.producer.service.productproducer.Product;
import product.producer.service.productproducer.ProductProducer;

public class InventoryServiceImpl implements InventoryService {
	
	private final InventoryProducer inventoryProducer;
	private final ProductProducer productProducer;
	private Scanner sc;
    
	public InventoryServiceImpl(InventoryProducer inventoryProducer, ProductProducer productProducer) {
        this.inventoryProducer = inventoryProducer;
        this.productProducer = productProducer;
        sc = new Scanner(System.in);
    }
	
	@Override
	public void startInventoryService() {
		
		System.out.println("\n");
	    System.out.println(" ╔════════════════════════════════════════════════════╗ ");
	    System.out.println(" ║                                                    ║ ");
        System.out.println(" ║    ★★★★★ WELCOME TO THE INVENTORY MODULE ★★★★★  ║ ");
	    System.out.println(" ║                                                    ║ ");
    	System.out.println(" ╚════════════════════════════════════════════════════╝ ");
		
		while(true) {
		     System.out.println(" ══════════════════════════════════════════════════════ ");
		     System.out.println("     [1] ➕ Add Inventory                             ");
		     System.out.println("     [2] 🔍 View Inventory Item                      ");
		     System.out.println("     [3] 📜 View All Inventory Items                 ");
		     System.out.println("     [4] ✏️ Update Inventory                         ");
		     System.out.println("     [0] ⏪ Back to Main Menu                        ");
		     System.out.println(" ══════════════════════════════════════════════════════ ");
		     System.out.print("⏩ Enter your choice: ");

			
			int choice = sc.nextInt();
			
			switch (choice) {
            case 1:
                addInventory();
                break;
                
            case 2:
            	viewInventory();
            	break;
            	
            case 3:
            	viewAllInventory();
            	break;
            case 4:
            	updateInventory();
            	break;
            case 0:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice, please try again.");
			}
			
		}
		
	}

	@Override
	public void addInventory() {
		System.out.println("\n");
		System.out.println(" ╔════════════════════════════════════════════════╗ ");
		System.out.println(" ║            📦 AVAILABLE PRODUCTS 📦             ║ ");
		System.out.println(" ╚════════════════════════════════════════════════╝ ");
		System.out.println("═════════════════════════════════════════════════════");

		// Fetch available products
		List<Product> products = productProducer.getAllProducts();

		if (products.isEmpty()) {
		    System.out.println("⚠️ No products available to add to inventory.");
		    return;
		}

		// Display Products in a table-like format
		System.out.printf(" %-10s │ %-20s │ %-10s %n", "🆔 ID", "📦 Product Name", "💰 Price ($)");
		System.out.println("═════════════════════════════════════════════════════");

		for (Product product : products) {
		    System.out.printf(" %-10d │ %-20s │ %-10.2f %n", 
		        product.getId(), product.getName(), product.getPrice());
		}

		System.out.println("═════════════════════════════════════════════════════");

		System.out.print("🔢 Enter the Product ID to add to inventory: ");
		if (!sc.hasNextInt()) {
		    System.out.println("❌ Invalid input. Please enter a valid numeric Product ID.");
		    sc.next();
		    return;
		}

		int productId = sc.nextInt();
		sc.nextLine(); 

		Product selectedProduct = products.stream()
		        .filter(p -> p.getId() == productId)
		        .findFirst()
		        .orElse(null);

		if (selectedProduct == null) {
		    System.out.println("❌ Invalid Product ID. Please select a valid product.");
		    return;
		}

		System.out.print("📦 Enter quantity: ");
		if (!sc.hasNextInt()) {
		    System.out.println("❌ Invalid input. Please enter a valid numeric quantity.");
		    sc.next();
		    return;
		}

		int quantity = sc.nextInt();
		sc.nextLine(); 

		Inventory newInventory = new Inventory(productId, selectedProduct.getName(), quantity);

		boolean success = inventoryProducer.addInventory(newInventory);

		System.out.println("\n═════════════════════════════════════════════════════");
		if (success) {
		    System.out.println("✅ SUCCESS: " + quantity + " units of " + selectedProduct.getName() + " added to inventory!");
		} else {
		    System.out.println("❌ ERROR: Failed to add inventory item. Please try again.");
		}
		System.out.println("═════════════════════════════════════════════════════");
	}

	@Override
	public void viewInventory() {
		System.out.println("\n╔════════════════════════════════════════════════╗ ");
        System.out.println("║               🔍 VIEW INVENTORY ITEM           ║ ");
        System.out.println("╚════════════════════════════════════════════════╝ ");
		
	    while (true) { 
	        
	        System.out.print("🔢 Enter the Product ID to view inventory details (Press 0 to go back): ");

	        if (!sc.hasNextInt()) {
	            System.out.println("\n❌ Invalid input. Please enter a valid numeric Product ID.");
	            sc.next(); 
	            continue;
	        }

	        int productId = sc.nextInt();
	        sc.nextLine(); 

	        if (productId == 0) {
	            System.out.println("\n🔙 Returning to Inventory Menu...");
	            break;
	        }

	        System.out.println("\n🔄 Fetching inventory details...");

	        Inventory inventoryDetails = inventoryProducer.getInventory(productId);

	        System.out.println("\n═══════════════════════════════════════════════════");
	        if (inventoryDetails == null) {
	            System.out.println("❌ Inventory item not found for Product ID: " + productId);
	        } else {
	            System.out.println("📦 INVENTORY DETAILS:");
	            System.out.println("---------------------------------------------------");
	            System.out.printf("🆔 Product ID   : %d%n", inventoryDetails.getProductId());
	            System.out.printf("📦 Product Name : %s%n", inventoryDetails.getProductName());
	            System.out.printf("📊 Quantity     : %d units%n", inventoryDetails.getQuantity());
	            System.out.println("---------------------------------------------------");
	        }
	        System.out.println("═══════════════════════════════════════════════════\n");
	    }
	}

	@Override
	public void viewAllInventory() {
	    System.out.println("\n╔════════════════════════════════════════════════════════╗ ");
	    System.out.println("║                📦 VIEW ALL INVENTORY ITEMS             ║ ");
	    System.out.println("╚════════════════════════════════════════════════════════╝ ");

	    System.out.println("🔄 Fetching all inventory items...");

	    List<Inventory> inventoryList = inventoryProducer.viewAllInventory();

	    if (inventoryList.isEmpty()) {
	        System.out.println("\n❌ No inventory items available.");
	    } else {
	        System.out.println("\n══════════════════════════════════════════════════════════════════════════════");
	        System.out.printf(" %-10s │ %-25s │ %-10s %n", "🆔 Product ID", "📦 Product Name", "📊 Quantity");
	        System.out.println("══════════════════════════════════════════════════════════════════════════════");

	        for (Inventory inventory : inventoryList) {
	            System.out.printf(" %-10d │ %-25s │ %-10d %n", 
	                inventory.getProductId(), inventory.getProductName(), inventory.getQuantity());
	        }
	        
	        System.out.println("══════════════════════════════════════════════════════════════════════════════");
	    }

	    System.out.println("\n✅ Completed fetching inventory items.\n");
	}

	@Override
	public void updateInventory() {
	    System.out.println("\n╔════════════════════════════════════════════════╗ ");
	    System.out.println("║              ✏️ UPDATE INVENTORY ITEM          ║ ");
	    System.out.println("╚════════════════════════════════════════════════╝ ");

	    System.out.print("🔢 Enter the Product ID to update inventory: ");
	    
	    if (!sc.hasNextInt()) {
	        System.out.println("\n❌ Invalid input. Please enter a valid numeric Product ID.");
	        sc.next();
	        return;
	    }
	    
	    int productId = sc.nextInt();
	    sc.nextLine(); 

	    System.out.println("\n🔄 Fetching inventory details...");
	    
	    Inventory existingInventory = inventoryProducer.getInventory(productId);
	    
	    if (existingInventory == null) {
	        System.out.println("\n❌ No inventory found for Product ID: " + productId);
	        return;
	    }

	    System.out.println("\n📦 Current Inventory Details:");
	    System.out.println("═══════════════════════════════════════════════");
	    System.out.printf(" 🆔 Product ID   : %d%n", existingInventory.getProductId());
	    System.out.printf(" 📦 Product Name : %s%n", existingInventory.getProductName());
	    System.out.printf(" 📊 Current Qty  : %d units%n", existingInventory.getQuantity());
	    System.out.println("═══════════════════════════════════════════════");

	    System.out.print("\n✏️ Enter the new quantity: ");
	    
	    if (!sc.hasNextInt()) {
	        System.out.println("\n❌ Invalid input. Please enter a valid numeric quantity.");
	        sc.next();
	        return;
	    }

	    int newQuantity = sc.nextInt();
	    sc.nextLine(); 

	    Inventory updatedInventory = new Inventory(productId, existingInventory.getProductName(), newQuantity);

	    System.out.println("\n🔄 Updating inventory...");

	    boolean success = inventoryProducer.updateInventory(updatedInventory);

	    System.out.println("\n═══════════════════════════════════════════════");
	    if (success) {
	        System.out.println("✅ Inventory updated successfully!");
	        System.out.printf(" 🆔 Product ID   : %d%n", updatedInventory.getProductId());
	        System.out.printf(" 📦 Product Name : %s%n", updatedInventory.getProductName());
	        System.out.printf(" 📊 New Quantity : %d units%n", updatedInventory.getQuantity());
	    } else {
	        System.out.println("❌ Failed to update inventory. Item may not exist.");
	    }
	    System.out.println("═══════════════════════════════════════════════\n");
	}

}
