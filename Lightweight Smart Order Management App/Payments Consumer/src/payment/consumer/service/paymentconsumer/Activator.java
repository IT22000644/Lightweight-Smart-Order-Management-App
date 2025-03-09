package payment.consumer.service.paymentconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
<<<<<<< HEAD
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
		// TODO Auto-generated method stub
		
	}

	
=======

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
>>>>>>> e8ed20711934050731b1b172d6d2b2495cbc11e3

}
