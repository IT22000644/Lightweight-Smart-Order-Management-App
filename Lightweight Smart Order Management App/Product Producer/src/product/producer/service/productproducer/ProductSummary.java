package product.producer.service.productproducer;

public class ProductSummary {
    private int totalProducts;
    private int totalCategories;
    private double lowestPrice;
    private double highestPrice;
    private int highestSameCategoryCount;
    private int lowestSameCategoryCount;

    
    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(int totalCategories) {
        this.totalCategories = totalCategories;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public int getHighestSameCategoryCount() {
        return highestSameCategoryCount;
    }

    public void setHighestSameCategoryCount(int highestSameCategoryCount) {
        this.highestSameCategoryCount = highestSameCategoryCount;
    }

    public int getLowestSameCategoryCount() {
        return lowestSameCategoryCount;
    }

    public void setLowestSameCategoryCount(int lowestSameCategoryCount) {
        this.lowestSameCategoryCount = lowestSameCategoryCount;
    }
}
