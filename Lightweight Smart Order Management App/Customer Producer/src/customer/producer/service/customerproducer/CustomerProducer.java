package customer.producer.service.customerproducer;

import java.util.List;

public interface CustomerProducer {
	public void addCustomer();
	public Customer getCustomer();
	public List<Customer> getCustomers();
	public void updateCustomer();
	public void deleteCustomer();
}
