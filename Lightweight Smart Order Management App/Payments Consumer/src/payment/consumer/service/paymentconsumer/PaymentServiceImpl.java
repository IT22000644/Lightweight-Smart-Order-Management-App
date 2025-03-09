package payment.consumer.service.paymentconsumer;

import java.util.Scanner;

import payment.producer.service.paymentproducer.PaymentProducer;

public class PaymentServiceImpl implements PaymentService {
	
	private PaymentProducer paymentProducer;
	
	public PaymentServiceImpl(PaymentProducer paymentProducer) {
		this.paymentProducer = paymentProducer;
	}

	@Override
	public void startPaymentService() {
		Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (true) {
        	System.out.println("\n");
            System.out.println(" ╔════════════════════════════════════════════════════╗ ");
            System.out.println(" ║                                                    ║ ");
            System.out.println(" ║    ★★★★★ WELCOME TO THE PAYMENTS MODULE ★★★★★   ║ ");
            System.out.println(" ║                                                    ║ ");
            System.out.println(" ╚════════════════════════════════════════════════════╝ ");
            System.out.println(" ══════════════════════════════════════════════════════ ");
            System.out.println("     [1] 📜 View Payments                            ");
            System.out.println("     [2] 📋 View Payment Details                     ");
            System.out.println("     [3] ❌ Delete Payment                           ");
            System.out.println("     [0] ⏪ Back to Main Menu                        ");
            System.out.println(" ══════════════════════════════════════════════════════ ");
            System.out.print("⏩ Enter your choice: ");
            
            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("-> Customer Added Successfully");
                        break;

                    case 2:
                        viewPayments();
                        break;

                    case 3:
                        viewPayment();
                        break;

                    case 4:
                    	deletePayment();
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
	public void viewPayments() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewPayment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePayment() {
		// TODO Auto-generated method stub
		
	}

}
