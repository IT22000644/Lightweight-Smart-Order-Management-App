package supplier.consumer.service.supplierconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import supplier.producer.service.supplierproducer.SupplierProducer;

public class Activator implements BundleActivator {

    private ServiceRegistration<SupplierService> serviceRegistration;
    private ServiceReference<SupplierProducer> supplierProducerRef;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting Supplier Consumer...");

        supplierProducerRef = context.getServiceReference(SupplierProducer.class);

        if (supplierProducerRef != null) {
            SupplierProducer supplierProducer = context.getService(supplierProducerRef);
            SupplierService supplierService = new SupplierServiceImpl(supplierProducer);

            serviceRegistration = context.registerService(SupplierService.class, supplierService, null);

            System.out.println("Supplier Consumer Service Registered!");
            
        } else {
            System.out.println("Supplier Producer Service not available!");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
        System.out.println("Supplier Consumer Bundle Stopped!");
    }
}
