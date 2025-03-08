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
			System.out.println("Enter your choice (press 1 to save to Database or 0 exit) : ");
			
			int choice = sc.nextInt();
			
			switch (choice) {
            case 1:
                orderProducer.saveToDB();
                System.out.println("-> Save Success");
                break;

            case 0:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice, please try again.");
        }
			
		}
		
		
	}
	

}
