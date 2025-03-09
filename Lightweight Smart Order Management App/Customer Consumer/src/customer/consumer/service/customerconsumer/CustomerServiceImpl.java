package customer.consumer.service.customerconsumer;

import java.util.Scanner;

import customer.producer.service.customerproducer.CustomerProducer;

public class CustomerServiceImpl implements CustomerService {
	
	private CustomerProducer customerProducer;
	
	public CustomerServiceImpl (CustomerProducer customerProducer) {
		this.customerProducer = customerProducer;
	}

	@Override
	public void startCustomerService() {
		Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.println("\n");
            System.out.println(" ╔═════════════════════════════════════════════╗ ");
            System.out.println(" ║                                             ║ ");
            System.out.println(" ║     ★★★★★ WELCOME TO THE ★★★★★           ║ ");
            System.out.println(" ║              CUSTOMER MODULE                ║ ");
            System.out.println(" ║                                             ║ ");
            System.out.println(" ╚═════════════════════════════════════════════╝ ");
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
                        System.out.println("-> Customer Added Successfully");
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
		// TODO Auto-generated method stub
		
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
