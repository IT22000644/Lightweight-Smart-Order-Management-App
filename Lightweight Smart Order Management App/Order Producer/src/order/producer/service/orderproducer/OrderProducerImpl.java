package order.producer.service.orderproducer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProducerImpl implements OrderProducer {
	
	private Connection conn;
	
	public OrderProducerImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void createOrder(Order order) {
		String orderSql = "INSERT INTO orders (customer_id, total_amount) VALUES (?, ?)";
        String orderProductSql = "INSERT INTO OrderProducts (order_id, product_id, quantity) VALUES (?, ?, ?)";
        
        try (PreparedStatement orderStmt = conn.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement orderProductStmt = conn.prepareStatement(orderProductSql)) {
            
            orderStmt.setInt(1, order.getCustomerId());
            orderStmt.setDouble(2, order.getTotalAmount());
            orderStmt.executeUpdate();
            
            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                
                for (Map.Entry<Integer, Integer> entry : order.getProductQuantities().entrySet()) {
                    orderProductStmt.setInt(1, orderId);
                    orderProductStmt.setInt(2, entry.getKey());
                    orderProductStmt.setInt(3, entry.getValue());
                    orderProductStmt.addBatch();
                }
                orderProductStmt.executeBatch();
            }
            
            System.out.println("Order created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public Order getOrder(int id) {
		String orderSql = "SELECT * FROM orders WHERE order_id = ?";
        String orderProductSql = "SELECT * FROM OrderProducts WHERE order_id = ?";
        
        try (PreparedStatement orderStmt = conn.prepareStatement(orderSql);
             PreparedStatement orderProductStmt = conn.prepareStatement(orderProductSql)) {
            
            orderStmt.setInt(1, id);
            ResultSet orderRs = orderStmt.executeQuery();
            if (orderRs.next()) {
                int customerId = orderRs.getInt("customer_id");
                double totalAmount = orderRs.getDouble("total_amount");
                
                orderProductStmt.setInt(1, id);
                ResultSet orderProductRs = orderProductStmt.executeQuery();
                Map<Integer, Integer> productQuantities = new HashMap<>();
                while (orderProductRs.next()) {
                    int productId = orderProductRs.getInt("product_id");
                    int quantity = orderProductRs.getInt("quantity");
                    productQuantities.put(productId, quantity);
                }
                
                return new Order(id, customerId, productQuantities, totalAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
		
	}

	@Override
	public List<Order> getOrders() {
		List<Order> orders = new ArrayList<>();
        String orderSql = "SELECT * FROM orders";
        String orderProductSql = "SELECT * FROM OrderProducts WHERE order_id = ?";
        
        try (PreparedStatement orderStmt = conn.prepareStatement(orderSql);
             PreparedStatement orderProductStmt = conn.prepareStatement(orderProductSql)) {
            
            ResultSet orderRs = orderStmt.executeQuery();
            while (orderRs.next()) {
                int orderId = orderRs.getInt("order_id");
                int customerId = orderRs.getInt("customer_id");
                double totalAmount = orderRs.getDouble("total_amount");
                
                orderProductStmt.setInt(1, orderId);
                ResultSet orderProductRs = orderProductStmt.executeQuery();
                Map<Integer, Integer> productQuantities = new HashMap<>();
                while (orderProductRs.next()) {
                    int productId = orderProductRs.getInt("product_id");
                    int quantity = orderProductRs.getInt("quantity");
                    productQuantities.put(productId, quantity);
                }
                
                orders.add(new Order(orderId, customerId, productQuantities, totalAmount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
	}

	@Override
	public boolean deleteOrder(int id) {
		String orderSql = "DELETE FROM orders WHERE order_id = ?";
        String orderProductSql = "DELETE FROM OrderProducts WHERE order_id = ?";
        
        try (PreparedStatement orderStmt = conn.prepareStatement(orderSql);
             PreparedStatement orderProductStmt = conn.prepareStatement(orderProductSql)) {
            
            orderProductStmt.setInt(1, id);
            orderProductStmt.executeUpdate();
            
            orderStmt.setInt(1, id);
            orderStmt.executeUpdate();
            
            System.out.println("Order deleted successfully.");
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
		
	}

}
