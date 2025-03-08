package product.consumer.service.productconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import product.producer.service.productproducer.ProductProducer;

public class Activator implements BundleActivator {

    private ServiceRegistration<ProductService> serviceRegistration;
    private ServiceReference<ProductProducer> productProducerRef;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting Product Consumer...");

        productProducerRef = context.getServiceReference(ProductProducer.class);

        if (productProducerRef != null) {
            ProductProducer productProducer = context.getService(productProducerRef);
            ProductService productService = new ProductServiceImpl(productProducer);

            serviceRegistration = context.registerService(ProductService.class, productService, null);

            System.out.println("Product Consumer Service Registered!");
        } else {
            System.out.println("Product Producer Service not available!");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }

        System.out.println("Product Consumer Bundle Stopped!");
    }
}
