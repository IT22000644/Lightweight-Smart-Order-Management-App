package order.consumer.service.orderconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import customer.producer.service.customerproducer.CustomerProducer;
import inventory.producer.service.inventoryproducer.InventoryProducer;
import order.producer.service.orderproducer.OrderProducer;
import payment.producer.service.paymentproducer.PaymentProducer;
import product.producer.service.productproducer.ProductProducer;


public class Activator implements BundleActivator {

	private ServiceRegistration<OrderService> serviceRegistration;
	private ServiceReference<OrderProducer> orderProducerRef;
	private ServiceReference<ProductProducer> productProducerRef;
	private ServiceReference<CustomerProducer> customerProducerRef;
	private ServiceReference<InventoryProducer> inventoryProducerRef;
	private ServiceReference<PaymentProducer> paymentProducerRef;
	

	public void start(BundleContext context) throws Exception {
		
		orderProducerRef = context.getServiceReference(OrderProducer.class);
		productProducerRef = context.getServiceReference(ProductProducer.class);
		customerProducerRef = context.getServiceReference(CustomerProducer.class);
		inventoryProducerRef = context.getServiceReference(InventoryProducer.class);
		paymentProducerRef =  context.getServiceReference(PaymentProducer.class);
		
		if (orderProducerRef != null && productProducerRef != null && customerProducerRef != null && inventoryProducerRef != null && paymentProducerRef != null) {
			
			OrderProducer orderProducer = context.getService(orderProducerRef);
			ProductProducer productProducer = context.getService(productProducerRef);
			CustomerProducer customerProducer = context.getService(customerProducerRef);
			InventoryProducer inventoryProducer = context.getService(inventoryProducerRef);
			PaymentProducer paymentProducer = context.getService(paymentProducerRef);
			
			OrderService orderService = new OrderServiceImpl(orderProducer, productProducer, customerProducer, inventoryProducer, paymentProducer);
			
			serviceRegistration = context.registerService(OrderService.class, orderService, null);
			
			System.out.println("Order Service Bundle Started");
		}
	}

	public void stop(BundleContext context) throws Exception {
		if (serviceRegistration != null ) {                      
			serviceRegistration.unregister();                    
		}                                                        
		                                                         
		System.out.println("Order Consumer Bundle Stopped!");
	}

}
