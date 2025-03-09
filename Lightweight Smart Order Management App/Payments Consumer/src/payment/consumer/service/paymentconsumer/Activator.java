package payment.consumer.service.paymentconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import payment.producer.service.paymentproducer.PaymentProducer;



public class Activator implements BundleActivator {
	
	ServiceRegistration<PaymentService> serviceRegistration;
	ServiceReference<PaymentProducer> paymentProducerRef;

	@Override
	public void start(BundleContext context) throws Exception {
		paymentProducerRef = context.getServiceReference(PaymentProducer.class);
		
		if (paymentProducerRef != null) {
			
			PaymentProducer paymentProducer = context.getService(paymentProducerRef);
			PaymentService paymentService = new PaymentServiceImpl(paymentProducer);
			
			serviceRegistration = context.registerService(PaymentService.class, paymentService, null);
			
			System.out.println("Order Service Bundle Started");
			
		}
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (serviceRegistration != null ) {                      
			serviceRegistration.unregister();                    
		}                                                        
		                                                         
		System.out.println("Payment Consumer Bundle Stopped!");
		
	}

	
}
