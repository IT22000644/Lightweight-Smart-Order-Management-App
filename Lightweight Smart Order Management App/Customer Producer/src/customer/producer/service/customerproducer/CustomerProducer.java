package customer.producer.service.customerproducer;

import java.util.List;

public interface CustomerProducer {
	public boolean addCustomer(Customer customer);
	public Customer getCustomer(int customerId);
	public List<Customer> getCustomers();
	public boolean updateCustomer(Customer customer);
	public boolean deleteCustomer(int customerId);
}
