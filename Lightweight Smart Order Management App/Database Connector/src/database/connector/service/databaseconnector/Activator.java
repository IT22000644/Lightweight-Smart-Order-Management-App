package database.connector.service.databaseconnector;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	
	private ServiceRegistration<DatabaseService> serviceRegistration;

	@Override
	public void start(BundleContext context) throws Exception {
		
		DatabaseService databaseService = new DatabaseServiceImpl();
		
		serviceRegistration = context.registerService(DatabaseService.class, databaseService, null);
	
		System.out.println("Database Connector Bundle Started!");
	}

	public void stop(BundleContext context) throws Exception {
		
		if (serviceRegistration != null ) {
			serviceRegistration.unregister();
		}
		
		System.out.println("Database Connector Bundle Stopped!");
	}

}
