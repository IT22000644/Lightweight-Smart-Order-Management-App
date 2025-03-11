package supplier.producer.service.supplierproducer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierProducerImpl implements SupplierProducer {

    private Connection conn;

    public SupplierProducerImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void saveToDB(Supplier supplier) {
        String sql = "INSERT INTO suppliers (name, specializing_category, contact_number, email, location, description) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getSpecializingCategory());
            stmt.setString(3, supplier.getContactNumber());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getLocation());
            stmt.setString(6, supplier.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT id, name, specializing_category, contact_number, email, location, description FROM suppliers";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Supplier supplier = new Supplier(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specializing_category"),
                    rs.getString("contact_number"),
                    rs.getString("email"),
                    rs.getString("location"),
                    rs.getString("description")
                );
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    @Override
    public void updateSupplier(int id, String name, String specializingCategory, String contactNumber, String email, String location, String description) {
        String selectQuery = "SELECT name, specializing_category, contact_number, email, location, description FROM suppliers WHERE id = ?";
        String updateQuery = "UPDATE suppliers SET name = ?, specializing_category = ?, contact_number = ?, email = ?, location = ?, description = ? WHERE id = ?";

        try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            selectStmt.setInt(1, id);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String newName = (name == null || name.isEmpty()) ? rs.getString("name") : name;
                String newCategory = (specializingCategory == null || specializingCategory.isEmpty()) ? rs.getString("specializing_category") : specializingCategory;
                String newContact = (contactNumber == null || contactNumber.isEmpty()) ? rs.getString("contact_number") : contactNumber;
                String newEmail = (email == null || email.isEmpty()) ? rs.getString("email") : email;
                String newLocation = (location == null || location.isEmpty()) ? rs.getString("location") : location;
                String newDescription = (description == null || description.isEmpty()) ? rs.getString("description") : description;

                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, newName);
                    updateStmt.setString(2, newCategory);
                    updateStmt.setString(3, newContact);
                    updateStmt.setString(4, newEmail);
                    updateStmt.setString(5, newLocation);
                    updateStmt.setString(6, newDescription);
                    updateStmt.setInt(7, id);
                    updateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSupplier(int id) {
        String sql = "DELETE FROM suppliers WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier getSupplier(int supplierId) {
        String sql = "SELECT * FROM suppliers WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Supplier(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specializing_category"),
                    rs.getString("contact_number"),
                    rs.getString("email"),
                    rs.getString("location"),
                    rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Supplier> getSuppliersByName(String name) {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT id, name, specializing_category, contact_number, email, location, description FROM suppliers WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                suppliers.add(new Supplier(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specializing_category"),
                    rs.getString("contact_number"),
                    rs.getString("email"),
                    rs.getString("location"),
                    rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }
    
}
