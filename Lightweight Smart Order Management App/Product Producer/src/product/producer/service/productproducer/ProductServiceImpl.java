package product.producer.service.productproducer;

import java.util.ArrayList;
import java.util.List;
import database.connector.service.databaseconnector.DatabaseService;
import java.sql.*;


public class ProductServiceImpl {
    
	//database connection
	private final DatabaseService databaseService;
	
	public ProductServiceImpl(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}
	
	//Add a product
	
	public void addProduct(Product product) {
		String sql = "INSERT INTO products VALUES (?, ?, ?, ?)";
		try (Connection conn = databaseService.getConnection();
				PreparedStatement prepstmt = conn.prepareStatement(sql) ) {
			prepstmt.setString(1, product.getName());
			prepstmt.setString(2,  product.getCategory());
			prepstmt.setString(3, product.getDescription());
			prepstmt.setDouble(4, product.getPrice());
			prepstmt.executeUpdate();
			System.out.println("Product added: " + product.getName());
		} catch (SQLException error) {
			error.printStackTrace();
		}		
	}
	
	//Get All Products
	public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = databaseService.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
	
	//Update an Existing Product
	public void updateProduct(Product product) {
		String sql = "UPDATE products SET name = ?, category = ?, description = ?, price = ? WHERE ID = ?";
		try (Connection conn = databaseService.getConnection();
				PreparedStatement prepstmt = conn.prepareStatement(sql)) {
			
			prepstmt.setString(1, product.getName());
			prepstmt.setString(2, product.getCategory());
			prepstmt.setString(3, product.getDescription());
			prepstmt.setDouble(4, product.getPrice());
			prepstmt.setInt(4, product.getId());
			prepstmt.executeUpdate();
			System.out.println("Product updated: ID" + product.getId());
		} catch (SQLException error) {
			error.printStackTrace();
		}
	}
	
	//Delete an Existing Product
	public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = databaseService.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Product deleted: ID " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	

}