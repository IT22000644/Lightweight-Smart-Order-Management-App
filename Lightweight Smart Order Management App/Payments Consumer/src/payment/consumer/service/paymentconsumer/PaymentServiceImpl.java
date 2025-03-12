package payment.consumer.service.paymentconsumer;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import payment.producer.service.paymentproducer.PaymentProducer;
import payment.producer.service.paymentproducer.Payment;


public class PaymentServiceImpl implements PaymentService {
	
	private PaymentProducer paymentProducer;
	
	public PaymentServiceImpl(PaymentProducer paymentProducer) {
		this.paymentProducer = paymentProducer;
	}

	@Override
	public void startPaymentService() {
		Scanner scanner = new Scanner(System.in);
        

        while (true) {
        	System.out.println("\n");
            System.out.println(" ╔════════════════════════════════════════════════════╗ ");
            System.out.println(" ║                                                    ║ ");
            System.out.println(" ║    ★★★★★ WELCOME TO THE PAYMENTS MODULE ★★★★★   ║ ");
            System.out.println(" ║                                                    ║ ");
            System.out.println(" ╚════════════════════════════════════════════════════╝ ");
            System.out.println(" ══════════════════════════════════════════════════════ ");
            System.out.println("     [1] 📜 Add Payments                                ");
            System.out.println("     [2] 📋 View Payment Details                        ");
            System.out.println("     [3] ✅ View All Payment Details                    ");
            System.out.println("     [4] ✏️ Update Payment                              ");
            System.out.println("     [5] ❌ Delete Payment                              ");
            System.out.println("     [0] ⏪ Back to Main Menu                           ");
            System.out.println(" ══════════════════════════════════════════════════════ ");
            System.out.print("⏩ Enter your choice: ");
            
            int choice = -1;
            
            while (true) {
                try {
                    choice = scanner.nextInt(); 
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number. \n");
                    System.out.print("Enter your choice: ");
                    scanner.nextLine(); 
                }
            }


                switch (choice) {

                    case 1:
                        addPayments(scanner); //Override add payment method from payment.consumer
                        break;

                    case 2:
                        viewPayment();  //Override view payment method from payment.consumer
                        break;

                    case 3:
                    	listAllPayments(); //Override view all payment method from payment.consumer
                        break; 
                    case 4:
                    	updatePayment(); //Override update payment method from payment.consumer
                        break;    
                        
                   case 5:
                    	deletePayment(); //Override delete payment method from payment.consumer
                   	    break;

                   case 0:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice, please try again.");
                }

        }
		
	}

	
	@Override // Add payment for database
	public void addPayments(Scanner scanner) {
		
		List<String> validMethods = Arrays.asList("Credit Card","Debit Card","PayPal","Bank Transfer","Cash"); //Make a array for get valid methods
		List<String> valideStatus = Arrays.asList("Pending","Completed","Failed","Refunded"); // Make a array for get valid status
	    
	    System.out.print("\nEnter the order ID for the payment: "); //Getting order ID
	    while (!scanner.hasNextInt()) {
	        System.out.println("Invalid input. Please enter a numeric order ID."); //Validate order Id
	        scanner.next();
	    }
	    int orderId = scanner.nextInt();
	    scanner.nextLine();

	    String paymentMethod;
	    while (true) {
	        System.out.print("Enter payment method (Credit Card, Debit Card, PayPal, Bank Transfer, Cash): "); //Getting payment methods
	        paymentMethod = scanner.nextLine().trim();

	        if (validMethods.contains(paymentMethod)) {  //Validate payment methods
	            break; 
	        } else {
	            System.out.println("Error: Invalid payment method. Please enter a valid option.");
	        }
	    }

	    double amount = 0.0;
	    boolean validAmount = false;
	    while (!validAmount) {
	        System.out.print("Enter payment amount: "); //Getting payment amount
	        try {
	            amount = Double.parseDouble(scanner.nextLine()); //Validate payment methods
	            if (amount <= 0) {
	                System.out.println("Amount must be greater than 0. Please try again."); //Can't be 0 or (-)
	            } else {
	                validAmount = true;
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input! Please enter a valid amount.");
	        }
	    }

	    
        String status;
	    while (true) {
	        System.out.print("Enter payment status (Pending, Completed, Failed, Refunded): ");  //Getting payment status
	        status = scanner.nextLine().trim(); 

	        if (valideStatus.contains(status)) { //Validate payment status using above array
	            break; 
	        } else {
	            System.out.println("Error: Invalid payment status. Please enter 'Pending' or 'Completed'.");
	        }
	    }


	   
	    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

	   
	    Payment payment = new Payment(0, orderId, amount, paymentMethod, status, currentTimestamp, currentTimestamp);  // Create a payment object with the details

	    
	    paymentProducer.createPayment(payment); // Assuming this method exists in the producer

	    System.out.println("Payment added successfully!"); //Show success message
		
		
	}


	@Override //View payment by payment Id
	public void viewPayment() {
	    Scanner scanner = new Scanner(System.in);
	    System.out.print("\nEnter the payment ID to view details: ");  // Requesting payment ID
	    int paymentId = scanner.nextInt();
	    
	    Payment payment = paymentProducer.getPayment(paymentId); //Get database call for payment by Id(Producer call)
	    if (payment == null) {
	        System.out.println("Payment not found."); //Show not found if haven't get any
	    } else {
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Format Time stamps to string
	        String createdAtFormatted = dateFormat.format(payment.getCreatedAt());
	        String updatedAtFormatted = dateFormat.format(payment.getUpdatedAt());
	        
	        System.out.printf("\nPayment Details: \nID: %d\nOrder ID: %d\nAmount: %.2f\nPayment Method: %s\nStatus: %s\nCreated At: %s\nUpdated At: %s\n",
	                payment.getId(), payment.getOrderId(), payment.getAmount(),
	                payment.getPaymentMethod(), payment.getStatus(),
	                createdAtFormatted, updatedAtFormatted); // Fetch to the console
	    }
	}
	
	
	@Override //Get all payments
	public void listAllPayments() {
		Scanner scanner = new Scanner(System.in);
	    List<Payment> payments = paymentProducer.getAllPayments(); // Fetch all payments

	    if (payments.isEmpty()) {
	        System.out.println("No payments available."); //If any payment are not in database
	    } else {
	        // Print table header
	        System.out.printf("%-5s | %-10s | %-10s | %-15s | %-10s | %-20s | %-20s\n",
	                "ID", "Order ID", "Amount", "Payment Method", "Status", "Created At", "Updated At");
	        System.out.println("---------------------------------------------------------------------------------------------");

	        // Print each payment in a row
	        for (Payment payment : payments) {
	            System.out.printf("%-5d | %-10d | %-10.2f | %-15s | %-10s | %-20s | %-20s\n",
	                    payment.getId(), payment.getOrderId(), payment.getAmount(),
	                    payment.getPaymentMethod(), payment.getStatus(),
	                    payment.getCreatedAt(), payment.getUpdatedAt()); // Fetch data to the console
	        }
	    }

	}

	@Override //Delete payment by ID
	public void deletePayment() {
	    Scanner scanner = new Scanner(System.in);

	    // Step 1: Show all payments before deletion
	    List<Payment> payments = paymentProducer.getAllPayments(); //Get all payments from database

	    if (payments.isEmpty()) {
	        System.out.println("No payments available."); //Show if payments unavailable 
	        return;
	    }

	    // Display payments in table format
	    System.out.println("\nAll Payments:");
	    System.out.printf("%-5s | %-10s | %-10s | %-15s | %-10s | %-20s | %-20s\n",
	            "ID", "Order ID", "Amount", "Payment Method", "Status", "Created At", "Updated At");
	    System.out.println("---------------------------------------------------------------------------------------------");

	    for (Payment payment : payments) {
	        System.out.printf("%-5d | %-10d | %-10.2f | %-15s | %-10s | %-20s | %-20s\n",
	                payment.getId(), payment.getOrderId(), payment.getAmount(),
	                payment.getPaymentMethod(), payment.getStatus(),
	                payment.getCreatedAt(), payment.getUpdatedAt()); // Fetch data to console as a Table
	    }

	    // Step 2: Ask for payment ID to delete
	    System.out.print("\nEnter the payment ID to delete: "); //Asking want to delete payment ID
	    int paymentId = scanner.nextInt();
	    scanner.nextLine(); // Consume newline character

	    // Step 3: Confirm deletion
	    System.out.print("\nAre you sure you want to delete this payment? (y/n): "); //Confirm deleting 
	    String confirmation = scanner.nextLine().trim();

	    if (confirmation.equalsIgnoreCase("y")) {
	        paymentProducer.deletePayment(paymentId);
	        System.out.println("\nPayment deleted successfully."); //Show success message
	    } else {
	        System.out.println("\nPayment was not deleted."); //Show unsuccess message
	    }
	}


	
	 @Override //Update payment by ID
	 public void updatePayment() {
	        Scanner scanner = new Scanner(System.in);
	        
	        List<Payment> payments = paymentProducer.getAllPayments();

		    if (payments.isEmpty()) {
		        System.out.println("No payments available.");
		        return;
		    }

		    // Display payments in table format
		    System.out.println("\nAll Payments:");
		    System.out.printf("%-5s | %-10s | %-10s | %-15s | %-10s | %-20s | %-20s\n",
		            "ID", "Order ID", "Amount", "Payment Method", "Status", "Created At", "Updated At");
		    System.out.println("---------------------------------------------------------------------------------------------");

		    for (Payment payment : payments) {
		        System.out.printf("%-5d | %-10d | %-10.2f | %-15s | %-10s | %-20s | %-20s\n",
		                payment.getId(), payment.getOrderId(), payment.getAmount(),
		                payment.getPaymentMethod(), payment.getStatus(),
		                payment.getCreatedAt(), payment.getUpdatedAt());
		    }

		    
	        System.out.print("\nEnter the payment ID to update: ");
	        int paymentId = scanner.nextInt();
	        
	        Payment payment = paymentProducer.getPayment(paymentId);
	        if (payment == null) {
	            System.out.println("Payment not found.");
	            return;
	        }

	        scanner.nextLine(); // consume newline
	        
	        System.out.println("Current Payment Details:");
	        System.out.println("Amount: " + payment.getAmount());
	        System.out.println("Payment Method: " + payment.getPaymentMethod());
	        System.out.println("Status: " + payment.getStatus());
	       
	        
	        System.out.print("\nEnter new amount (or press Enter to keep unchanged): ");
	        String newAmount = scanner.nextLine();
	        Double updatedAmount = payment.getAmount(); // Default to existing amount

	        if (!newAmount.isEmpty()) {
	            try {
	                double amount = Double.parseDouble(newAmount);
	                if (amount <= 0) {
	                    System.out.println("Error: Amount must be greater than 0. Keeping the original amount."); //Validate amount more than 0
	                } else {
	                    updatedAmount = amount;
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("Error: Invalid input! Keeping the original amount.");
	            }
	        }

	        // List of valid payment methods
	        List<String> validMethods = Arrays.asList("Credit Card","Debit Card","PayPal","Bank Transfer","Cash");  

	        System.out.print("Enter new payment method (or press Enter to keep unchanged): ");
	        String newPaymentMethod = scanner.nextLine().trim();
	        String updatedPaymentMethod = payment.getPaymentMethod(); // Default to existing method

	        if (!newPaymentMethod.isEmpty()) {
	            if (validMethods.contains(newPaymentMethod)) {
	                updatedPaymentMethod = newPaymentMethod;
	            } else {
	                System.out.println("Error: Invalid payment method. Keeping the original method."); //If got failed the process stay as save original data 
	            }
	        }

	        // List of valid statuses
	        List<String> validStatuses = Arrays.asList("Pending","Completed","Failed","Refunded");

	        System.out.print("Enter new status (or press Enter to keep unchanged): "); 
	        String newStatus = scanner.nextLine().trim();
	        String updatedStatus = payment.getStatus(); // Default to existing status

	        if (!newStatus.isEmpty()) {
	            if (validStatuses.contains(newStatus)) {
	                updatedStatus = newStatus;
	            } else {
	                System.out.println("Error: Invalid status. Keeping the original status.");
	            }
	        }


	        
	        paymentProducer.updatePayment(paymentId, updatedAmount, updatedPaymentMethod, updatedStatus);
	    }


}
