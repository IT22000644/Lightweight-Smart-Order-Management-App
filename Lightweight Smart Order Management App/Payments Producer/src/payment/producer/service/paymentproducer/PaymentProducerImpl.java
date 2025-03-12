package payment.producer.service.paymentproducer;

import java.sql.Connection;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class PaymentProducerImpl implements PaymentProducer {
	
	private Connection conn; //Establish the DB connection - MYsql
	
	public PaymentProducerImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void createPayment(Payment payment) { //Add payment for DB
		
        String sql = "INSERT INTO payments (order_id, amount, payment_method, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)"; //SQL query for insert data
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getOrderId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setString(4, payment.getStatus());
            stmt.setTimestamp(5, payment.getCreatedAt());
            stmt.setTimestamp(6, payment.getUpdatedAt());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();  //Error fetching in DB
        }
	}

	@Override
	public Payment getPayment(int id) { //Get payments by ID
		
	    String sql = "SELECT * FROM payments WHERE id = ?"; //SQL query for search data by ID

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return new Payment(
	                rs.getInt("id"),
	                rs.getInt("order_id"),
	                rs.getDouble("amount"),
	                rs.getString("payment_method"),
	                rs.getString("status"), 
	                rs.getTimestamp("created_at"),
	                rs.getTimestamp("updated_at")
	            );
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace(); //Error fetching in DB
	    }
	    return null;
	}


	@Override
	public List<Payment> getAllPayments() { //Get all payments from DB
		
	    List<Payment> payments = new ArrayList<>(); //Store payments for array list
	    
	    String sql = "SELECT * FROM payments"; // Select all payments 

	    try (PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            payments.add(new Payment(
	                rs.getInt("id"),
	                rs.getInt("order_id"),
	                rs.getDouble("amount"),
	                rs.getString("payment_method"),
	                rs.getString("status"),
	                rs.getTimestamp("created_at"),
	                rs.getTimestamp("updated_at")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  //Error fetching in DB
	    }
	    return payments;
	}

	
	
	@Override
	public void updatePayment(int id, Double amount, String paymentMethod, String status) { //Update payments from DB using ID
		
	    String selectQuery = "SELECT amount, payment_method, status FROM payments WHERE id = ?"; // Select payment from DB 
	    String updateQuery = "UPDATE payments SET amount = ?, payment_method = ?, status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?"; //Update payment to DB

	    try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
	        selectStmt.setInt(1, id);  // Ensure you're only setting one parameter here (the id)
	        ResultSet rs = selectStmt.executeQuery(); //Select the DB using quarry

	        if (rs.next()) {
	        	
	            // If parameter is null, keep the existing value
	            Double newAmount = (amount != null) ? amount : rs.getDouble("amount");
	            String newPaymentMethod = (paymentMethod != null && !paymentMethod.isEmpty()) ? paymentMethod : rs.getString("payment_method");
	            String newStatus = (status != null && !status.isEmpty()) ? status : rs.getString("status");

	            // Now update the record with new values
	            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
	              
	                updateStmt.setDouble(1, newAmount);  
	                updateStmt.setString(2, newPaymentMethod);  
	                updateStmt.setString(3, newStatus); 
	                updateStmt.setInt(4, id);  

	                int rowsUpdated = updateStmt.executeUpdate(); //Update the DB using quarry
	                if (rowsUpdated > 0) {
	                    System.out.println("Payment updated successfully.");
	                } else {
	                    System.out.println("No payment found with the given ID.");
	                }
	            }
	        } else {
	            System.out.println("Payment not found."); // Showing feedback if payment not found
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}




	@Override
	public void deletePayment(int id) { //Delete the payment using DB
		
		String sql = "DELETE FROM payments WHERE id = ?";  // Delete payment from DB quarry
 
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { //Delete payment using SQL quarry
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Payment deleted successfully.");
            } else {
                System.out.println("Payment not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

}
