package order.consumer.service.orderconsumer;

import java.util.Scanner;

import order.producer.service.orderproducer.OrderProducer;

public class OrderServiceImpl implements OrderService {
	
	private OrderProducer orderProducer;
	
	public OrderServiceImpl(OrderProducer orderProducer) {
		this.orderProducer = orderProducer;
	}
	
	public void startOrderService() {
		Scanner sc = new Scanner(System.in);
		
		
		while(true) {
			
			
			System.out.println("\n");
			System.out.println(" ╔═════════════════════════════════════════════╗ ");
			System.out.println(" ║                                             ║ ");
			System.out.println(" ║     ★★★★★ WELCOME TO THE ★★★★★           ║ ");
			System.out.println(" ║              ORDERS MODULE                  ║ ");
			System.out.println(" ║                                             ║ ");
			System.out.println(" ╚═════════════════════════════════════════════╝ ");
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
                orderProducer.saveToDB();
                System.out.println("-> Save Success");
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewOrderDetails() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewAllOrders() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOrder() {
		// TODO Auto-generated method stub
		
	}
	

}
