package product.consumer.service.productconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import product.producer.service.productproducer.ProductProducer;
import supplier.producer.service.supplierproducer.SupplierProducer;

public class Activator implements BundleActivator {

    private ServiceRegistration<ProductService> serviceRegistration;
    private ServiceReference<ProductProducer> productProducerRef;
    private ServiceReference<SupplierProducer> supplierProducerRef;
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting Product Consumer...");

        productProducerRef = context.getServiceReference(ProductProducer.class);
        supplierProducerRef = context.getServiceReference(SupplierProducer.class);
        
        if (productProducerRef != null && supplierProducerRef != null) {
            
            ProductProducer productProducer = context.getService(productProducerRef);
            SupplierProducer supplierProducer = context.getService(supplierProducerRef);

            
            ProductService productService = new ProductServiceImpl(productProducer, supplierProducer);
            serviceRegistration = context.registerService(ProductService.class, productService, null);

            System.out.println("Product Consumer Service Registered!");
        } else {
            
            if (productProducerRef == null) {
                System.out.println("Product Producer Service not available!");
            }
            if (supplierProducerRef == null) {
                System.out.println("Supplier Producer Service not available!");
            }
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
