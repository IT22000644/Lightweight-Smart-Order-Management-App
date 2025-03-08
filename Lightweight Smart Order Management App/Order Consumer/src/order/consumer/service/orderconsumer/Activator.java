package order.consumer.service.orderconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import order.producer.service.orderproducer.OrderProducer;


public class Activator implements BundleActivator {

	private ServiceRegistration<OrderService> serviceRegistration;
	private ServiceReference<OrderProducer> orderProducerRef;

	public void start(BundleContext context) throws Exception {
		
		orderProducerRef = context.getServiceReference(OrderProducer.class);
		
		if (orderProducerRef != null) {
			
			OrderProducer orderProducer = context.getService(orderProducerRef);
			OrderService orderService = new OrderServiceImpl(orderProducer);
			
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
