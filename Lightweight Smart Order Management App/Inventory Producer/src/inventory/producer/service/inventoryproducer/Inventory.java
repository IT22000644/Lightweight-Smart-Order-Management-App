package inventory.producer.service.inventoryproducer;

public class Inventory {
    private int productId;
    private String productName;
    private int quantity;
    
    public Inventory(int productId, String productName, int quantity) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
	}

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

	public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
