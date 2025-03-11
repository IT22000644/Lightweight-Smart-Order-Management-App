package supplier.consumer.service.supplierconsumer;

import java.util.Scanner;

public interface SupplierService {
    void startSupplierService();
    void listAllSuppliers();
    void updateSupplier(Scanner sc);
    void deleteSupplier(Scanner sc);
    void saveSupplier(Scanner sc);
}
