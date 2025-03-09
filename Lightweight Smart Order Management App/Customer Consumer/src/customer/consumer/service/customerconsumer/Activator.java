package customer.consumer.service.customerconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import customer.producer.service.customerproducer.CustomerProducer;

public class Activator implements BundleActivator {
	
	ServiceRegistration<CustomerService> serviceRegistration;
	ServiceReference<CustomerProducer> customerProducerRef;

	@Override
	public void start(BundleContext context) throws Exception {
		customerProducerRef = context.getServiceReference(CustomerProducer.class);
		
		if (customerProducerRef != null) {
			CustomerProducer customerProducer = context.getService(customerProducerRef);
			CustomerService customerService = new CustomerServiceImpl(customerProducer);
			
			serviceRegistration = context.registerService(CustomerService.class, customerService, null);
		
			System.out.println("Customer Service Bundle Started");
		}
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (serviceRegistration != null ) {                      
			serviceRegistration.unregister();                    
		}                                                        
		                                                         
		System.out.println("Customer Consumer Bundle Stopped!");
		
	}

	

}
