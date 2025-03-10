package inventory.producer.service.inventoryproducer;

import java.sql.Connection;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import database.connector.service.databaseconnector.DatabaseService;

public class Activator implements BundleActivator {
	
	private ServiceReference<DatabaseService> dbServiceReference;
	private ServiceRegistration<InventoryProducer> serviceRegistration;

	@Override
	public void start(BundleContext context) throws Exception {
		dbServiceReference = context.getServiceReference(DatabaseService.class);
		
		if (dbServiceReference != null) {
			DatabaseService dbConnectionService = context.getService(dbServiceReference);
			
			Connection conn = dbConnectionService.getConnection();
			
			InventoryProducer inventoryProducer = new InventoryProducerImpl(conn);
			
			serviceRegistration = context.registerService(InventoryProducer.class, inventoryProducer, null);
		} else {
			System.out.println("DatabaseConnection service not found.");
		}
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (serviceRegistration != null ) {                      
			serviceRegistration.unregister();                    
		}                                                        
		
		System.out.println("Inventory Producer Stopped");
		
	}


}
