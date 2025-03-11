package product.producer.service.productproducer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import supplier.producer.service.supplierproducer.*;

public class ProductProducerImpl implements ProductProducer {

    private Connection conn;
    

    public ProductProducerImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void saveToDB(Product product) {
        String getSupplierIdQuery = "SELECT id FROM suppliers WHERE name = ?";
        String insertProductQuery = "INSERT INTO products (name, category, description, price, supplier_id) VALUES (?, ?, ?, ?, ?)";

        try {
            // Check if the supplier exists
            PreparedStatement getSupplierStmt = conn.prepareStatement(getSupplierIdQuery);
            getSupplierStmt.setString(1, product.getSupplier().getName()); 
            ResultSet rs = getSupplierStmt.executeQuery();

            if (rs.next()) {
                int supplierId = rs.getInt("id");

                // If supplier is found, insert the product
                try (PreparedStatement insertStmt = conn.prepareStatement(insertProductQuery)) {
                    insertStmt.setString(1, product.getName());
                    insertStmt.setString(2, product.getCategory());
                    insertStmt.setString(3, product.getDescription());
                    insertStmt.setDouble(4, product.getPrice());
                    insertStmt.setInt(5, supplierId);
                    insertStmt.executeUpdate();
                    System.out.println("Product added successfully.");
                }
            } else {
                // If no supplier found, exit and display a message
                System.out.println("No suppliers found with the name: " + product.getSupplier().getName());
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.category, p.description, p.price, p.supplier_id, s.name AS supplier_name " +
                     "FROM products p " +
                     "JOIN suppliers s ON p.supplier_id = s.id"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int supplierId = rs.getInt("supplier_id");
                String supplierName = rs.getString("supplier_name");

                Supplier supplier = new Supplier(supplierId, supplierName);

                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    supplier  
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    @Override
    public Product getProduct(int productId) {
        String sql = "SELECT p.id, p.name, p.category, p.description, p.price, p.supplier_id, s.name AS supplier_name " +
                     "FROM products p " +
                     "JOIN suppliers s ON p.supplier_id = s.id " +
                     "WHERE p.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId); 

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int supplierId = rs.getInt("supplier_id");
                    String supplierName = rs.getString("supplier_name");

                    Supplier supplier = new Supplier(supplierId, supplierName); 

                    return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        supplier  
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  
    }

    @Override
    public void updateProduct(int id, String name, String category, String description, Double price, Supplier supplier) {
        String selectQuery = "SELECT name, category, description, price, supplier_id FROM products WHERE id = ?";
        String updateQuery = "UPDATE products SET name = ?, category = ?, description = ?, price = ?, supplier_id = ? WHERE id = ?";

        try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            selectStmt.setInt(1, id);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String newName = (name == null || name.isEmpty()) ? rs.getString("name") : name;
                String newCategory = (category == null || category.isEmpty()) ? rs.getString("category") : category;
                String newDescription = (description == null || description.isEmpty()) ? rs.getString("description") : description;
                Double newPrice = (price == null) ? rs.getDouble("price") : price;
                int newSupplierId = (supplier == null) ? rs.getInt("supplier_id") : supplier.getId();  // Get supplierId from Supplier object

                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, newName);
                    updateStmt.setString(2, newCategory);
                    updateStmt.setString(3, newDescription);
                    updateStmt.setDouble(4, newPrice);
                    updateStmt.setInt(5, newSupplierId);  
                    updateStmt.setInt(6, id);
                    updateStmt.executeUpdate();
                    System.out.println("Product updated successfully.");
                }
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> filterProducts(String name, String category, Double minPrice, Double maxPrice, String supplierName) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT p.id, p.name, p.category, p.description, p.price, p.supplier_id, s.name AS supplier_name " +
                "FROM products p " +
                "JOIN suppliers s ON p.supplier_id = s.id WHERE 1=1");

        // Add filtering conditions
        if (name != null && !name.isEmpty()) {
            sql.append(" AND p.name LIKE ?");
        }
        if (category != null && !category.isEmpty()) {
            sql.append(" AND p.category LIKE ?");
        }
        if (minPrice != null) {
            sql.append(" AND p.price >= ?");
        }
        if (maxPrice != null) {
            sql.append(" AND p.price <= ?");
        }
        if (supplierName != null && !supplierName.isEmpty()) {
            sql.append(" AND s.name LIKE ?");
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int index = 1;

            // Set parameters based on filters
            if (name != null && !name.isEmpty()) {
                stmt.setString(index++, "%" + name + "%");
            }
            if (category != null && !category.isEmpty()) {
                stmt.setString(index++, "%" + category + "%");
            }
            if (minPrice != null) {
                stmt.setDouble(index++, minPrice);
            }
            if (maxPrice != null) {
                stmt.setDouble(index++, maxPrice);
            }
            if (supplierName != null && !supplierName.isEmpty()) {
                stmt.setString(index++, "%" + supplierName + "%");
            }

            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            new Supplier(rs.getInt("supplier_id"), rs.getString("supplier_name"))
                    );
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    
    @Override
    public ProductSummary getProductSummary() {
        List<Product> products = getAllProducts();  
        ProductSummary summary = new ProductSummary();

        double lowestPrice = Double.MAX_VALUE;
        double highestPrice = Double.MIN_VALUE;
        int highestSameCategoryCount = 0;
        int lowestSameCategoryCount = Integer.MAX_VALUE;

        Map<String, Integer> categoryCountMap = new HashMap<>();

        for (Product product : products) {
            categoryCountMap.put(product.getCategory(),
                    categoryCountMap.getOrDefault(product.getCategory(), 0) + 1);

            if (product.getPrice() < lowestPrice) {
                lowestPrice = product.getPrice();
            }
            if (product.getPrice() > highestPrice) {
                highestPrice = product.getPrice();
            }
        }

        for (Map.Entry<String, Integer> entry : categoryCountMap.entrySet()) {
            int count = entry.getValue();
            if (count > highestSameCategoryCount) {
                highestSameCategoryCount = count;
            }
            if (count < lowestSameCategoryCount) {
                lowestSameCategoryCount = count;
            }
        }

        summary.setTotalProducts(products.size());
        summary.setTotalCategories(categoryCountMap.size());
        summary.setLowestPrice(lowestPrice);
        summary.setHighestPrice(highestPrice);
        summary.setHighestSameCategoryCount(highestSameCategoryCount);
        summary.setLowestSameCategoryCount(lowestSameCategoryCount);

        return summary;
    }

    @Override
    public double getProductPrice(int productId) {
        String sql = "SELECT price FROM products WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public Map<String, Integer> getCategoryCount() {
        Map<String, Integer> categoryCountMap = new HashMap<>();
        String sql = "SELECT category, COUNT(*) FROM products GROUP BY category";

        try (PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categoryCountMap.put(rs.getString("category"), rs.getInt("COUNT(*)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryCountMap;
    }

}
