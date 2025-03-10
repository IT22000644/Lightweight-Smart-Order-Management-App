package inventory.producer.service.inventoryproducer;

import java.util.List;

public interface InventoryProducer {
	public boolean addInventory(Inventory inventory);
	public Inventory getInventory(int productId);
	public List<Inventory> viewAllInventory();
	public boolean updateInventory(Inventory inventory);
	public void deductInventory(int productId, int quantity);
	public boolean restockProduct(int productId, int quantity);
	public int getAvailableQuantity(int productId);
}
