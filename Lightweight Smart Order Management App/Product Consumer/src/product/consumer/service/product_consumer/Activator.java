package product.consumer.service.product_consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import product.producer.service.ProductServiceImpl;
import product.consumer.service.ProductConsumer;

public class Activator implements BundleActivator {

    private static BundleContext context;
    private ProductConsumer productConsumer;  // Instance of ProductConsumer to invoke its logic

    static BundleContext getContext() {
        return context;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;

        // Create an instance of ProductConsumer
        productConsumer = new ProductConsumer();

        // Reference the ProductServiceImpl using the OSGi service reference
        ServiceReference<ProductServiceImpl> serviceReference = context.getServiceReference(ProductServiceImpl.class);
        if (serviceReference != null) {
            ProductServiceImpl productService = context.getService(serviceReference);
            
            // Inject the service and run the consumer logic
            if (productService != null) {
                productConsumer.start(bundleContext);  // Call the start method of ProductConsumer to simulate consuming the service
            }
        } else {
            System.out.println("No Product Service found!");
        }
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        // Stop the ProductConsumer logic (clean up if necessary)
        if (productConsumer != null) {
            productConsumer.stop(bundleContext);
        }
        Activator.context = null;
    }
}
