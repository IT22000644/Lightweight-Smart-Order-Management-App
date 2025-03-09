package customer.producer.service.customerproducer;

import java.sql.Connection;
import java.util.List;

public class CustomerProducerImpl implements CustomerProducer {
	
	private Connection conn;
	
	public CustomerProducerImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void addCustomer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Customer getCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCustomer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer() {
		// TODO Auto-generated method stub
		
	}

}
