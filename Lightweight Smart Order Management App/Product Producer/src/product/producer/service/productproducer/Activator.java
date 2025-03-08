package product.producer.service.productproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import database.connector.service.databaseconnector.DatabaseService;


public class Activator implements BundleActivator {
	
    private ServiceRegistration<ProductServiceImpl> serviceRegistration;
    private DatabaseService databaseService;
    
    @Override
    public void start(BundleContext context) throws Exception {
    	ServiceReference<DatabaseService> ref = context.getServiceReference(DatabaseService.class);
        if (ref != null) {
            databaseService = context.getService(ref);
            ProductServiceImpl productService = new ProductServiceImpl(databaseService);
            serviceRegistration = context.registerService(ProductServiceImpl.class, productService, null);
            System.out.println("Product Producer has started and service is registered.");
        } else {
            System.out.println("DatabaseService was not found. ProductService was not registered.");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    	if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
        System.out.println("Product Producer has stopped.");
    }
    
}