package order.producer.service.orderproducer;

import java.util.Map;

public class Order {
    private int orderId;
    private int customerId;
    private Map<Integer, Integer> productQuantities; // Map of product ID to quantity
    private double totalAmount;

    // Default constructor
    public Order() {
    }

    // Parameterized constructor
    public Order(int orderId, int customerId, Map<Integer, Integer> productQuantities, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productQuantities = productQuantities;
        this.totalAmount = totalAmount;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Map<Integer, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<Integer, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", productQuantities=" + productQuantities +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
