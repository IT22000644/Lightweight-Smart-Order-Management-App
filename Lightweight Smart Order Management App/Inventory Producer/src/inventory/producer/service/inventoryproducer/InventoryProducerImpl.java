package inventory.producer.service.inventoryproducer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryProducerImpl implements InventoryProducer {
	
	Connection conn;
	
	public InventoryProducerImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addInventory(Inventory inventory) {
	    String checkSql = "SELECT COUNT(*) FROM inventory WHERE product_id = ?";
	    String insertSql = "INSERT INTO inventory (product_id, product_name, quantity) VALUES (?, ?, ?)";
	    
	    try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
	        checkStmt.setInt(1, inventory.getProductId());
	        ResultSet rs = checkStmt.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            System.out.println("Inventory with product_id " + inventory.getProductId() + " already exists.");
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }

	    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
	        insertStmt.setInt(1, inventory.getProductId());
	        insertStmt.setString(2, inventory.getProductName());
	        insertStmt.setInt(3, inventory.getQuantity());
	        insertStmt.executeUpdate();
	        System.out.println("Inventory added successfully.");
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public Inventory getInventory(int productId) {
		String sql = "SELECT * FROM inventory WHERE product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Inventory(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}

	@Override
	public List<Inventory> viewAllInventory() {
		List<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM inventory";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Inventory inventory = new Inventory(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getInt("quantity")
                );
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
	}

	@Override
	public boolean updateInventory(Inventory inventory) {
		String sql = "UPDATE inventory SET product_name = ?, quantity = ? WHERE product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, inventory.getProductName());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setInt(3, inventory.getProductId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
		
	}

	@Override
	public void deductInventory(int productId, int quantity) {
		String sql = "UPDATE inventory SET quantity = quantity - ? WHERE product_id = ? AND quantity >= ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, quantity);
	        pstmt.setInt(2, productId);
	        pstmt.setInt(3, quantity);
	        
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Inventory deducted successfully.");
	        } else {
	            System.out.println("Failed to deduct inventory. Insufficient stock or invalid product ID.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}

	@Override
	public int getAvailableQuantity(int productId) {
		String sql = "SELECT quantity FROM inventory WHERE product_id = ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, productId);
	        
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("quantity");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return 0;
	}

	@Override
	public boolean restockProduct(int productId, int quantity) {
		String sql = "UPDATE inventory SET quantity = quantity + ? WHERE product_id = ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, quantity);
	        pstmt.setInt(2, productId);
	        
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            return true;
	        } else {
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
		

}
