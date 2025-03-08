package com.ordermanagement.mainapp;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import order.consumer.service.orderconsumer.OrderService;
import product.consumer.service.productconsumer.ProductService;


public class Activator implements BundleActivator {
	ServiceReference<?> serviceReference;
	ServiceReference<?> productServiceReference;
	
	
	private OrderService orderService;
	private ProductService productService;
	
	@Override 
	public void start(BundleContext context) throws Exception {
		System.out.println("Start Subscriber Service");
		serviceReference = context.getServiceReference(OrderService.class.getName());
		orderService = (OrderService) context.getService(serviceReference);
		
		 // Get ProductService
        productServiceReference = context.getServiceReference(ProductService.class.getName());
        productService = (ProductService) context.getService(productServiceReference);
        
		displayMenu();
		
	}
	
	private void displayMenu() {
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.println("\n=== Order Management Console ===");
            System.out.println("1. Order Management");
            System.out.println("2. Product Managment");
            System.out.println("3. Inventory Management");
            System.out.println("4. Payment Management");
            System.out.println("5. Customer Managment");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
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
		context.ungetService(serviceReference);
		context.ungetService(productServiceReference);
		
	
	}

	

}
