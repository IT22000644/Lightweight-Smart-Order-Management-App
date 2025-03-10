package customer.consumer.service.customerconsumer;

import java.util.List;
import java.util.Scanner;

import customer.producer.service.customerproducer.Customer;
import customer.producer.service.customerproducer.CustomerProducer;

public class CustomerServiceImpl implements CustomerService {
	
	private CustomerProducer customerProducer;
	private Scanner sc;
	
	public CustomerServiceImpl (CustomerProducer customerProducer) {
		this.customerProducer = customerProducer;
		this.sc = new Scanner(System.in);
	}

	@Override
	public void startCustomerService() {
        int choice = -1;
        
        System.out.println("\n");
        System.out.println(" ╔═════════════════════════════════════════════╗ ");
        System.out.println(" ║                                             ║ ");
        System.out.println(" ║     ★★★★★ WELCOME TO THE ★★★★★           ║ ");
        System.out.println(" ║              CUSTOMER MODULE                ║ ");
        System.out.println(" ║                                             ║ ");
        System.out.println(" ╚═════════════════════════════════════════════╝ ");

        while (true) {
            
        	System.out.println(" ══════════════════════════════════════════════ ");
        	System.out.println("     [1] 🧑‍🤝‍🧑 Add Customer                     ");
        	System.out.println("     [2] 📜 View Customers                    ");
        	System.out.println("     [3] 📋 View Customer Details             ");
        	System.out.println("     [4] ✏️ Edit Customer                     ");
        	System.out.println("     [5] 🗑 Delete Customer                   ");
        	System.out.println("     [0] ⏪ Back to Main Menu                 ");
        	System.out.println(" ══════════════════════════════════════════════ ");
        	System.out.print("⏩ Enter your choice: ");
            
            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addCustomer();
                        break;

                    case 2:
                        viewCustomers();
                        break;

                    case 3:
                        viewCustomer();
                        break;

                    case 4:
                        editCustomer();
                        break;
                        
                    case 5:
                        deleteCustomer();
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next();
            }
        }

		
	}

	@Override
	public void addCustomer() {
		System.out.println("\n╔════════════════════════════════════════╗");
	    System.out.println("║           🆕 ADD NEW CUSTOMER          ║");
	    System.out.println("╚════════════════════════════════════════╝");
	    
	    sc.nextLine();

	    System.out.print("🧑 Enter Customer Name: ");
	    String name = sc.nextLine().trim(); 

	    if (name.isEmpty()) {
	        System.out.println("❌ Customer name cannot be blank!");
	        return;
	    }

	    System.out.print("📧 Enter Email: ");
	    String email = sc.nextLine().trim();

	    System.out.print("📞 Enter Phone Number: ");
	    String phone = sc.nextLine().trim();

	    Customer newCustomer = new Customer(name, email, phone);

	    boolean success = customerProducer.addCustomer(newCustomer);

	    System.out.println("\n════════════════════════════════════════");
	    if (success) {
	        System.out.println("✅ Customer added successfully!");
	        System.out.println(newCustomer);
	    } else {
	        System.out.println("❌ Failed to add customer. Try again.");
	    }
	    System.out.println("════════════════════════════════════════\n");
		
	}

	@Override
	public void viewCustomers() {
		System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          📜 CUSTOMER LIST            ║");
        System.out.println("╚══════════════════════════════════════╝");

        List<Customer> customers = customerProducer.getCustomers();

        if (customers.isEmpty()) {
            System.out.println("❌ No customers found.");
        } else {
            System.out.printf("%-5s %-20s %-20s %-15s%n", "ID", "Name", "Email", "Phone");
            System.out.println("------------------------------------------------------------");

            for (Customer customer : customers) {
                System.out.printf("%-5d %-20s %-20s %-15s%n",
                        customer.getCustomerId(), customer.getCustomerName(), customer.getEmail(), customer.getPhoneNumber());
            }
        }
        System.out.println("════════════════════════════════════════\n");
		
	}

	@Override
	public void viewCustomer() {
		System.out.print("\n🔍 Enter Customer ID: ");
		int customerId = sc.nextInt();

		Customer customer = customerProducer.getCustomer(customerId);

		if (customer != null) {
		    System.out.println("\n╔════════════════════════════════════════╗");
		    System.out.println("║         📋 CUSTOMER DETAILS            ║");
		    System.out.println("╚════════════════════════════════════════╝");
		    System.out.println("🆔 ID: " + customer.getCustomerId());
		    System.out.println("🧑 Name: " + customer.getCustomerName());
		    System.out.println("📧 Email: " + customer.getEmail());
		    System.out.println("📞 Phone: " + customer.getPhoneNumber());
		    System.out.println("════════════════════════════════════════\n");
		} else {
		    System.out.println("❌ Customer not found.");
		}
		
	}

	@Override
	public void editCustomer() {
		System.out.print("\n✏️ Enter Customer ID to edit: ");
	    int customerId = sc.nextInt();
	    sc.nextLine(); // Consume newline

	    Customer customer = customerProducer.getCustomer(customerId);

	    if (customer == null) {
	        System.out.println("❌ Customer not found.");
	        return;
	    }

	    System.out.println("\n╔════════════════════════════════════════╗");
	    System.out.println("║         ✏️ EDIT CUSTOMER DETAILS       ║");
	    System.out.println("╚════════════════════════════════════════╝");
	    System.out.println("🆔 ID: " + customer.getCustomerId());
	    System.out.println("🧑 Current Name: " + customer.getCustomerName());
	    System.out.println("📧 Current Email: " + customer.getEmail());
	    System.out.println("📞 Current Phone: " + customer.getPhoneNumber());
	    System.out.println("════════════════════════════════════════\n");

	    System.out.print("🧑 Enter New Name (Press Enter to keep existing): ");
	    String newName = sc.nextLine().trim();
	    if (!newName.isEmpty()) {
	        customer.setCustomerName(newName);
	    }

	    System.out.print("📧 Enter New Email (Press Enter to keep existing): ");
	    String newEmail = sc.nextLine().trim();
	    if (!newEmail.isEmpty()) {
	        customer.setEmail(newEmail);
	    }

	    System.out.print("📞 Enter New Phone Number (Press Enter to keep existing): ");
	    String newPhone = sc.nextLine().trim();
	    if (!newPhone.isEmpty()) {
	        customer.setPhoneNumber(newPhone);
	    }

	    boolean success = customerProducer.updateCustomer(customer);

	    System.out.println("\n════════════════════════════════════════");
	    if (success) {
	        System.out.println("✅ Customer details updated successfully!");
	        System.out.println("🆔 ID: " + customer.getCustomerId());
	        System.out.println("🧑 Name: " + customer.getCustomerName());
	        System.out.println("📧 Email: " + customer.getEmail());
	        System.out.println("📞 Phone: " + customer.getPhoneNumber());
	    } else {
	        System.out.println("❌ Failed to update customer. Please try again.");
	    }
	    System.out.println("════════════════════════════════════════\n");
		
	}

	@Override
	public void deleteCustomer() {
		System.out.println("\n╔════════════════════════════════════════╗");
	    System.out.println("║          🗑 DELETE CUSTOMER            ║");
	    System.out.println("╚════════════════════════════════════════╝");

	    System.out.print("\n🗑 Enter Customer ID to Delete: ");
	    int customerId = sc.nextInt();

	    boolean success = customerProducer.deleteCustomer(customerId);

	    System.out.println("\n════════════════════════════════════════");
	    if (success) {
	        System.out.println("✅ Customer deleted successfully!");
	    } else {
	        System.out.println("❌ Failed to delete customer. Customer not found.");
	    }
	    System.out.println("════════════════════════════════════════\n");
	}

}
