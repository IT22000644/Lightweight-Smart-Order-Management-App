package order.producer.service.orderproducer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class OrderProducerImpl implements OrderProducer {
	
	private Connection conn;
	
	public OrderProducerImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void saveToDB() {
		
        String sql = "INSERT INTO test (name) VALUES (?)";
        
        String randomName = "Name_" + UUID.randomUUID().toString();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, randomName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
