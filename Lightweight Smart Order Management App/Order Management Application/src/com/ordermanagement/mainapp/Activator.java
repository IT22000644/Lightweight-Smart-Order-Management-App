package com.ordermanagement.mainapp;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import customer.consumer.service.customerconsumer.CustomerService;
import inventory.consumer.service.inventoryconsumer.InventoryService;
import order.consumer.service.orderconsumer.OrderService;
import payment.consumer.service.paymentconsumer.PaymentService;
import product.consumer.service.productconsumer.ProductService;


public class Activator implements BundleActivator {
	ServiceReference<?> orderServiceReference;
	ServiceReference<?> productServiceReference;
	ServiceReference<?> customerServiceReference;
	ServiceReference<?> paymentServiceReference;
	ServiceReference<?> inventoryServiceReference;
	
	
	private OrderService orderService;
	private ProductService productService;
	private CustomerService customerService;
	private PaymentService paymentService;
	private InventoryService inventoryService;
	
	@Override 
	public void start(BundleContext context) throws Exception {
		System.out.println("Start Subscriber Service");
		orderServiceReference = context.getServiceReference(OrderService.class);
        orderService = (orderServiceReference != null) ? (OrderService) context.getService(orderServiceReference) : null;

        productServiceReference = context.getServiceReference(ProductService.class);
        productService = (productServiceReference != null) ? (ProductService) context.getService(productServiceReference) : null;

        customerServiceReference = context.getServiceReference(CustomerService.class);
        customerService = (customerServiceReference != null) ? (CustomerService) context.getService(customerServiceReference) : null;

        paymentServiceReference = context.getServiceReference(PaymentService.class);
        paymentService = (paymentServiceReference != null) ? (PaymentService) context.getService(paymentServiceReference) : null;

        inventoryServiceReference = context.getServiceReference(InventoryService.class);
        inventoryService = (inventoryServiceReference != null) ? (InventoryService) context.getService(inventoryServiceReference) : null;
        
        
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
            case 3:
                if (inventoryService != null) {
                	inventoryService.startInventoryService();
                } else {
                    System.out.println("InventorService is not available, unable to manage products.");
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
		if (orderServiceReference != null) context.ungetService(orderServiceReference);
        if (productServiceReference != null) context.ungetService(productServiceReference);
        if (customerServiceReference != null) context.ungetService(customerServiceReference);
        if (paymentServiceReference != null) context.ungetService(paymentServiceReference);
        if (inventoryServiceReference != null) context.ungetService(inventoryServiceReference);	
	}

	

}
