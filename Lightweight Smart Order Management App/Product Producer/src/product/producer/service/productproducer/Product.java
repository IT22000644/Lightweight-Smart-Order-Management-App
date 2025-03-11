package product.producer.service.productproducer;

import supplier.producer.service.supplierproducer.Supplier;

public class Product {
	private int id;
    private String name;
    private String category;
    private String description;
    private double price;
    private Supplier supplier;
    
    public Product(int id, String name, String category, String description, double price, Supplier supplier ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Supplier getSupplier() {  
        return supplier;
    }

    public void setSupplier(Supplier supplier) { 
        this.supplier = supplier;
    }
}
