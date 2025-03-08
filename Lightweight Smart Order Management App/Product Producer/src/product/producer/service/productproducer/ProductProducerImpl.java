package product.producer.service.productproducer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductProducerImpl implements ProductProducer {

    private Connection conn;

    public ProductProducerImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void saveToDB(Product product) {
        String sql = "INSERT INTO products (name, category, description, price) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
