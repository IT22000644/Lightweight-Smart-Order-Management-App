package supplier.producer.service.supplierproducer;

import java.util.List;

public interface SupplierProducer {
    void saveToDB(Supplier supplier);
    List<Supplier> getAllSuppliers();
    void updateSupplier(int id, String name, String specializingCategory, String contactNumber, String email, String location, String description);
    void deleteSupplier(int id);
    Supplier getSupplier(int supplierId);
    List<Supplier> getSuppliersByName(String name);
}
