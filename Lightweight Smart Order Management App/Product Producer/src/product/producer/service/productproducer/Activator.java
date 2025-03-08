package product.producer.service.productproducer;

import java.io.Console;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;


public class Activator implements BundleActivator {
    private ServiceRegistration<ProductServiceImpl> serviceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        ProductServiceImpl productService = new ProductServiceImpl();
        serviceRegistration = context.registerService(ProductServiceImpl.class, productService, null);
        System.out.println("Product Producer started and service registered.");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        serviceRegistration.unregister();
        System.out.println("Product Producer stopped.");
    }
    
    
}