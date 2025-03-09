package inventory.producer.service.inventoryproducer;

import java.util.List;

public interface InventoryProducer {
	public void addInventory();
	public Inventory getInventory(int productId);
	public List<Inventory> viewAllInventory();
	public void updateInventory();
}
