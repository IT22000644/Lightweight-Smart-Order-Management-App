package customer.producer.service.customerproducer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerProducerImpl implements CustomerProducer {
	
	private Connection conn;
	
	public CustomerProducerImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addCustomer(Customer customer) {
		String sql = "INSERT INTO customers (customer_name, email, phone_number) VALUES (?, ?, ?)";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, customer.getCustomerName());
	        pstmt.setString(2, customer.getEmail());
	        pstmt.setString(3, customer.getPhoneNumber());
	        pstmt.executeUpdate();
	        System.out.println("Customer added successfully.");
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
		
	}

	@Override
	public Customer getCustomer(int customerId) {
		String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getString("email"),
                    rs.getString("phone_number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}

	@Override
	public List<Customer> getCustomers() {
		List<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getString("email"),
                    rs.getString("phone_number")
                );
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
	}

	@Override
	public boolean updateCustomer(Customer customer) {
		String sql = "UPDATE customers SET customer_name = ?, email = ?, phone_number = ? WHERE customer_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhoneNumber());
            pstmt.setInt(4, customer.getCustomerId());
            pstmt.executeUpdate();
            System.out.println("Customer updated successfully.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
		
	}

	@Override
	public boolean deleteCustomer(int customerId) {
		String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
            System.out.println("Customer deleted successfully.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
		
	}

}
