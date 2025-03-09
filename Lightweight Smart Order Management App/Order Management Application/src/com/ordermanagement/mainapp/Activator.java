package com.ordermanagement.mainapp;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import customer.consumer.service.customerconsumer.CustomerService;
import order.consumer.service.orderconsumer.OrderService;
import payment.consumer.service.paymentconsumer.PaymentService;
import product.consumer.service.productconsumer.ProductService;


public class Activator implements BundleActivator {
	ServiceReference<?> orderServiceReference;
	ServiceReference<?> productServiceReference;
	ServiceReference<?> customerServiceReference;
	ServiceReference<?> paymentServiceReference;
	
	
	private OrderService orderService;
	private ProductService productService;
	private CustomerService customerService;
	private PaymentService paymentService;
	
	@Override 
	public void start(BundleContext context) throws Exception {
		System.out.println("Start Subscriber Service");
		orderServiceReference = context.getServiceReference(OrderService.class.getName());
		orderService = (OrderService) context.getService(orderServiceReference);
		
		 // Get ProductService
        productServiceReference = context.getServiceReference(ProductService.class.getName());
        productService = (ProductService) context.getService(productServiceReference);
        
        customerServiceReference = context.getServiceReference(CustomerService.class.getName());
        customerService = (CustomerService) context.getService(customerServiceReference);
        
        paymentServiceReference = context.getServiceReference(PaymentService.class.getName());
        paymentService = (PaymentService) context.getService(paymentServiceReference);
        
        
		displayMenu();
		
	}
	
	private void displayMenu() {
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.println("\n");
			System.out.println("███████╗███╗   ███╗ █████╗ ██╗     ██╗      ██████╗ ███╗   ███╗███████╗");
			System.out.println("██╔════╝████╗ ████║██╔══██╗██║     ██║     ██╔═══██╗████╗ ████║██╔════╝");
			System.out.println("█████╗  ██╔████╔██║███████║██║     ██║     ██║   ██║██╔████╔██║█████╗  ");
			System.out.println("██╔══╝  ██║╚██╔╝██║██╔══██║██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝  ");
			System.out.println("███████╗██║ ╚═╝ ██║██║  ██║███████╗███████╗╚██████╔╝██║ ╚═╝ ██║███████╗");
			System.out.println("╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝");
			System.out.println("═══════════════════════════════════════════════════════════════════════");
			System.out.println("                      NEXT-GEN MANAGEMENT SYSTEM                        ");
			System.out.println("═══════════════════════════════════════════════════════════════════════");
			System.out.println("║ [1] 📦 Orders                    │ [4] 💳 Payments                   	║");
			System.out.println("║ [2] 🏷 Products                  │ [5] 👥 Customers                 	║");
			System.out.println("║ [3] 🏬 Inventory                 │ [0] ❌ Exit                        	║");
			System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
			System.out.print("⏩ Enter your choice: ");


            
            int choice = sc.nextInt();
            
            switch (choice) {
            case 1:
            	if (orderService != null) {
                    orderService.startOrderService();
                } else {
                    System.out.println("OrderService is not available, unable to place order.");
                }
                break;
            case 2:
                if (productService != null) {
                    productService.startProductService();
                } else {
                    System.out.println("ProductService is not available, unable to manage products.");
                }
                break;
            
            case 4:
                if (paymentService != null) {
                    paymentService.startPaymentService();
                } else {
                    System.out.println("CustomerService is not available, unable to manage products.");
                }
                break;
            case 5:
                if (customerService != null) {
                    customerService.startCustomerService();
                } else {
                    System.out.println("CustomerService is not available, unable to manage products.");
                }
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
	public void stop(BundleContext context) throws Exception {
		System.out.println("Shutting down Order Management Console.");
		context.ungetService(orderServiceReference);
		context.ungetService(productServiceReference);
		context.ungetService(customerServiceReference);
		context.ungetService(paymentServiceReference);
		
	
	}

	

}
