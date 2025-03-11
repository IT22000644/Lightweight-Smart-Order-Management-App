package supplier.producer.service.supplierproducer;

public class Supplier {
    private int id;
    private String name;
    private String specializingCategory;
    private String contactNumber;
    private String email;
    private String location;
    private String description;
    
    public Supplier(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Supplier(int id, String name, String specializingCategory, String contactNumber, String email, String location, String description) {
        this.id = id;
        this.name = name;
        this.specializingCategory = specializingCategory;
        this.contactNumber = contactNumber;
        this.email = email;
        this.location = location;
        this.description = description;
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

	public String getSpecializingCategory() {
		return specializingCategory;
	}

	public void setSpecializingCategory(String specializingCategory) {
		this.specializingCategory = specializingCategory;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

  
}
