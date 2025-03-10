package inventory.consumer.service.inventoryconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import inventory.producer.service.inventoryproducer.InventoryProducer;
import product.producer.service.productproducer.ProductProducer;


public class Activator implements BundleActivator {
	
	private ServiceRegistration<InventoryService> serviceRegistration;
	private ServiceReference<InventoryProducer> inventoryProducerRef;
	private ServiceReference<ProductProducer> productProducerRef;

	@Override
	public void start(BundleContext context) throws Exception {
		inventoryProducerRef = context.getServiceReference(InventoryProducer.class);
		productProducerRef = context.getServiceReference(ProductProducer.class);
		
		if (inventoryProducerRef != null) {
			
			InventoryProducer inventoryProducer = context.getService(inventoryProducerRef);
			ProductProducer productProducer = context.getService(productProducerRef);
			InventoryService inventoryService = new InventoryServiceImpl(inventoryProducer, productProducer);
			
			serviceRegistration = context.registerService(InventoryService.class, inventoryService, null);
			
			System.out.println("Inventory Service Bundle Started");
			
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (serviceRegistration != null ) {                      
			serviceRegistration.unregister();                    
		}                                                        
		                                                         
		System.out.println("Inventory Consumer Bundle Stopped!");
		
	}


}
