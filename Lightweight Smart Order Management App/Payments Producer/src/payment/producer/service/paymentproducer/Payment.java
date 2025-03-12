package payment.producer.service.paymentproducer;

import java.sql.Timestamp;

public class Payment {
    private int id;
    private int orderId;
    private double amount;
    private String paymentMethod;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructor
    public Payment(int id, int orderId, double amount, String paymentMethod, String status, 
                     Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

//    @Override
//    public String toString() {
//        return "Payment{" +
//                "id=" + id +
//                ", orderId=" + orderId +
//                ", amount=" + amount +
//                ", paymentMethod='" + paymentMethod + '\'' +
//                ", status='" + status + '\'' +
//                ", paymentDetails='" + paymentDetails + '\'' +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
//                '}';
//    }
}
