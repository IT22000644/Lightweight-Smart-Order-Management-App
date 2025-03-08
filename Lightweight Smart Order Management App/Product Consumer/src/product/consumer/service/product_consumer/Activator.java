package product.consumer.service.product_consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import product.producer.service.productproducer.ProductServiceImpl;


public class Activator implements BundleActivator {

    private static BundleContext context;
    private ProductConsumer productConsumer;  

    static BundleContext getContext() {
        return context;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;

        // Creating an Instance of ProductConsumer
        productConsumer = new ProductConsumer();

        // Reference the ProductServiceImpl using the OSGi service reference
        ServiceReference<ProductServiceImpl> serviceReference = context.getServiceReference(ProductServiceImpl.class);
        if (serviceReference != null) {
            ProductServiceImpl productService = context.getService(serviceReference);
            
            
            if (productService != null) {
                productConsumer.start(bundleContext);  
            }
        } else {
            System.out.println("No Product Service found!");
        }
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        if (productConsumer != null) {
            productConsumer.stop(bundleContext);
        }
        Activator.context = null;
    }
}