package order.consumer.service.orderconsumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import customer.producer.service.customerproducer.Customer;
import customer.producer.service.customerproducer.CustomerProducer;
import inventory.producer.service.inventoryproducer.InventoryProducer;
import order.producer.service.orderproducer.Order;
import order.producer.service.orderproducer.OrderProducer;
import payment.producer.service.paymentproducer.PaymentProducer;
import product.producer.service.productproducer.Product;
import product.producer.service.productproducer.ProductProducer;

public class OrderServiceImpl implements OrderService {
	
	private OrderProducer orderProducer;
	private ProductProducer productProducer;
	private CustomerProducer customerProducer;
	private InventoryProducer inventoryProducer;
	private PaymentProducer paymentProducer;
	private Scanner sc;
	
	public OrderServiceImpl(OrderProducer orderProducer, 
			ProductProducer productProducer, 
			CustomerProducer customerProducer, 
			InventoryProducer inventoryProducer, 
			PaymentProducer paymentProducer) {
		
		this.orderProducer = orderProducer;
		this.productProducer = productProducer;
		this.customerProducer = customerProducer;
		this.inventoryProducer = inventoryProducer;
		this.paymentProducer = paymentProducer;
		this.sc = new Scanner(System.in);
	}
	
	public void startOrderService() {
		System.out.println("\n");
		System.out.println(" ╔═════════════════════════════════════════════╗ ");
		System.out.println(" ║                                             ║ ");
		System.out.println(" ║     ★★★★★ WELCOME TO THE ★★★★★           ║ ");
		System.out.println(" ║              ORDERS MODULE                  ║ ");
		System.out.println(" ║                                             ║ ");
		System.out.println(" ╚═════════════════════════════════════════════╝ ");
		
		
		while(true) {
			System.out.println(" ══════════════════════════════════════════════ ");
			System.out.println("     [1] 🛒 Place Order                        ");
			System.out.println("     [2] 📜 View Orders                       ");
			System.out.println("     [3] 📋 View Order Details                ");
			System.out.println("     [4] ❌ Delete Order                      ");
			System.out.println("     [0] ⏪ Back to Main Menu                 ");
			System.out.println(" ══════════════════════════════════════════════ ");
			System.out.print("⏩ Enter your choice: ");

			
			int choice = sc.nextInt();
			
			switch (choice) {
            case 1:
                placeOrder();
                break;
                
            case 2:
            	viewAllOrders();
            	break;
            	
            case 3:
            	viewOrderDetails();
            	break;
            case 4:
            	deleteOrder();
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
	public void placeOrder() {
		System.out.println("\n");
	    System.out.println(" ╔═════════════════════════════════════════════════╗ ");
	    System.out.println(" ║              🛒 PLACE YOUR ORDER 🛒              ║ ");
	    System.out.println(" ║                                                 ║ ");
	    System.out.println(" ╚═════════════════════════════════════════════════╝ ");
	    System.out.println("\n");

	    System.out.println(" ╔═════════════════════════════════════════════════╗ ");
	    System.out.println(" ║               📜 Available Customers            ║ ");
	    System.out.println(" ╚═════════════════════════════════════════════════╝ ");
	    List<Customer> customers = customerProducer.getCustomers();  
	    if (customers != null && !customers.isEmpty()) {
	        for (Customer customer : customers) {
	            System.out.println(" 🧑‍💼 Customer ID: " + customer.getCustomerId() + " | Name: " + customer.getCustomerName());
	        }
	    } else {
	        System.out.println(" 🛑 No customers found.");
	        return;
	    }

	    System.out.print("\n📥 Enter Customer ID: ");
	    int customerId = sc.nextInt();

	    boolean customerExists = customers.stream().anyMatch(c -> c.getCustomerId() == customerId);
	    if (!customerExists) {
	        System.out.println(" ❌ Invalid Customer ID. Please try again.");
	        return;
	    }

	    System.out.println("\n╔═════════════════════════════════════════════════╗ ");
	    System.out.println("║                🛍️ Available Products            ║ ");
	    System.out.println("╚═════════════════════════════════════════════════╝ ");
	    List<Product> products = productProducer.getAllProducts();  
	    if (products != null && !products.isEmpty()) {
	        for (Product product : products) {
	            int productId = product.getId();
	            int availableQuantity = inventoryProducer.getAvailableQuantity(productId);  
	            System.out.println(" 🏷️ Product ID: " + productId + " | " + product.getName() + " | Available Quantity: " + availableQuantity);
	        }
	    } else {
	        System.out.println(" 🛑 No products found.");
	        return;
	    }

	    Map<Integer, Integer> productQuantities = new HashMap<>();
	    
    	System.out.print("\nAdd product to the order (y/n - n to stop): ");
        String response = sc.next();
        
	    while (!response.equalsIgnoreCase("n")) {

            System.out.print("📦 Enter Product ID: ");
            int productId = sc.nextInt();

            boolean productExists = products.stream().anyMatch(p -> p.getId() == productId);
            if (!productExists) {
                System.out.println(" ❌ Invalid Product ID. Please try again.");
                continue;
            }

            int availableQuantity = inventoryProducer.getAvailableQuantity(productId);
            System.out.print("🔢 Enter Quantity (Available: " + availableQuantity + "): ");
            int quantity = sc.nextInt();

            if (quantity > availableQuantity) {
                System.out.println(" ❌ You cannot order more than the available quantity (" + availableQuantity + ")");
                continue;
            }

            productQuantities.put(productId, quantity);
            
            
	    	System.out.print("\nAdd product to the order (y/n - n to stop): ");
	        response = sc.next();
	        
	    }

	    double totalAmount = 0.0;
	    for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
	        int productId = entry.getKey();
	        int quantity = entry.getValue();
	        double price = productProducer.getProductPrice(productId); 
	        totalAmount += price * quantity;
	    }


	    Order order = new Order();
	    order.setCustomerId(customerId);
	    order.setProductQuantities(productQuantities);
	    order.setTotalAmount(totalAmount);

	    orderProducer.createOrder(order);

	    for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
	        int productId = entry.getKey();
	        int quantity = entry.getValue();
	        inventoryProducer.deductInventory(productId, quantity);  
	    }

	    System.out.println("\n╔═════════════════════════════════════════════════╗ ");
	    System.out.println(" ║          ✅ Order placed successfully!          ║ ");
	    System.out.println(" ╚═════════════════════════════════════════════════╝\n\n\n ");
	    
		
	}

	@Override
	public void viewOrderDetails() {
		System.out.print("\n🔍 Enter Order ID to view details: ");
	    int orderId = sc.nextInt();

	    Order order = orderProducer.getOrder(orderId);

	    if (order != null) {
	        int customerId = order.getCustomerId();
	        Customer customer = customerProducer.getCustomer(customerId);  

	        System.out.println("\n╔═════════════════════════════════════════════════════╗");
	        System.out.println("║                   🛒 Order Details                  ║");
	        System.out.println("╚═════════════════════════════════════════════════════╝");

	        System.out.println("\n👤 Customer Details:");
	        System.out.println("╔═════════════════════════════════════════════════════╗");
	        if (customer != null) {
	            System.out.printf("║ %-51s ║\n", "Name        : " + customer.getCustomerName());
	            System.out.printf("║ %-51s ║\n", "Customer ID : " + customer.getCustomerId());
	            System.out.printf("║ %-51s ║\n", "Email       : " + customer.getEmail()); 
	            System.out.printf("║ %-51s ║\n", "Phone       : " + customer.getPhoneNumber());  
	        } else {
	            System.out.println("║ Customer not found.                               ║");
	        }
	        System.out.println("╚═════════════════════════════════════════════════════╝");

	        System.out.println("\n🛍️ Product Details:");
	        System.out.println("╔═════════════════════╦════════════════════╦════════════╦════════════════╗");
	        System.out.println("║ Product ID          ║ Product Name       ║ Quantity   ║ Total Price    ║");
	        System.out.println("╠═════════════════════╬════════════════════╬════════════╬════════════════╣");

	        if (order.getProductQuantities() != null && !order.getProductQuantities().isEmpty()) {
	            for (Map.Entry<Integer, Integer> entry : order.getProductQuantities().entrySet()) {
	                int productId = entry.getKey();
	                int quantity = entry.getValue();

	                Product product = productProducer.getProduct(productId);  
	                if (product != null) {
	                    String productName = product.getName();
	                    double price = productProducer.getProductPrice(productId); 
	                    double totalProductPrice = price * quantity;

	                    System.out.printf("║ %-19d ║ %-18s ║ %-10d ║ %-14.2f ║\n", productId, productName, quantity, totalProductPrice);
	                } else {
	                    System.out.printf("║ %-19d ║ %-18s ║ %-10s ║ %-14s ║\n", productId, "Product Not Found", "N/A", "N/A");
	                }
	            }
	        } else {
	            System.out.println("║ No products found in the order.                       ║");
	        }
	        System.out.println("╚═════════════════════╩════════════════════╩════════════╩════════════════╝");

	        System.out.println("\n══════════════════════════════════════════════════════");
	        System.out.println("  💰 Total Amount: " + order.getTotalAmount());
	        System.out.println("══════════════════════════════════════════════════════");
	    } else {
	        System.out.println("\n❌ Order not found.");
	    }
		
	}

	@Override
	public void viewAllOrders() {
		System.out.println("\nFetching all orders...");

	    List<Order> orders = orderProducer.getOrders();

	    if (orders != null && !orders.isEmpty()) {
	        System.out.println("╔════════════════════╦════════════════════╦════════════════╗");
	        System.out.println("║ Order ID           ║ Customer Name      ║ Total Amount   ║");
	        System.out.println("╠════════════════════╬════════════════════╬════════════════╣");

	        for (Order order : orders) {
	            int orderId = order.getOrderId();
	            int customerId = order.getCustomerId();
	            double totalAmount = order.getTotalAmount();

	            Customer customer = customerProducer.getCustomer(customerId);  
	            String customerName = (customer != null) ? customer.getCustomerName() : "Unknown";

	            System.out.printf("║ %-18d ║ %-18s ║ %-14.2f ║\n", orderId, customerName, totalAmount);
	        }

	        System.out.println("╚════════════════════╩════════════════════╩════════════════╝");
	    } else {
	        System.out.println("\n❌ No orders found.");
	    }
		
	}

	@Override
	public void deleteOrder() {
		System.out.println("\n🛑 Deleting an Order...");

	    System.out.print("Enter Order ID to delete: ");
	    int orderId = sc.nextInt();

	    Order order = orderProducer.getOrder(orderId);  

	    if (order != null) {
	        restockInventory(order);
	        
	        boolean success = orderProducer.deleteOrder(orderId);

	        if (success) {
	            System.out.println("\n✅ Order deleted successfully!");
	        } else {
	            System.out.println("\n❌ Failed to delete order. Please try again.");
	        }
	    } else {
	        System.out.println("\n❌ Order not found. Please check the Order ID and try again.");
	    }
		
	}
	
	private void restockInventory(Order order) {
	    System.out.println("\n🔄 Restocking inventory for deleted order...");

	    for (Map.Entry<Integer, Integer> entry : order.getProductQuantities().entrySet()) {
	        int productId = entry.getKey();
	        int quantity = entry.getValue();
	        
	        boolean restocked = inventoryProducer.restockProduct(productId, quantity);

	        if (restocked) {
	            System.out.printf("Product ID: %-5d | Restocked Quantity: %-5d\n", productId, quantity);
	        } else {
	            System.out.printf("Product ID: %-5d | Failed to restock\n", productId);
	        }
	    }
	    System.out.println("\n🔄 Inventory restocked successfully!");
	}
	

}
