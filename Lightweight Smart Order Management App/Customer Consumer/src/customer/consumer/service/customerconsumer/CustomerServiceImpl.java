package customer.consumer.service.customerconsumer;

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
            System.out.println("     [4] ✏️ Edit Customer                    ");
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewCustomer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editCustomer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer() {
		// TODO Auto-generated method stub
		
	}

}
