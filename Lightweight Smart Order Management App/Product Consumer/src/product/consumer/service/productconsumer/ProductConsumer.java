package product.consumer.service.productconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import product.producer.service.productproducer.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class ProductConsumer implements BundleActivator {

    // Service reference for ProductServiceImpl
    private ServiceReference<ProductServiceImpl> serviceReference;
    
    private List<String> productList = new ArrayList<>();

    // Method to add a product
    public void addProduct(String name, double price) {
        String product = name + " - $" + price;
        productList.add(product);
        System.out.println("Added Product: " + product);
    }

    // Method to get all products
    public List<String> getProducts() {
        return productList;
    }

    @Override
    public void start(BundleContext context) throws Exception {
        // Reference to the ProductServiceImpl
        serviceReference = context.getServiceReference(ProductServiceImpl.class);

        if (serviceReference != null) {
            ProductServiceImpl productService = context.getService(serviceReference);
            
            // Consume the service (add products and retrieve them)
            addProduct("Apple", 1.5);
            addProduct("Banana", 0.8);
            
            List<String> products = getProducts();
            System.out.println("Available Products:");
            for (String product : products) {
                System.out.println(product);
            }
        } else {
            System.out.println("No Product Service found!");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        context.ungetService(serviceReference);
        System.out.println("Product Consumer stopped.");
    }
}