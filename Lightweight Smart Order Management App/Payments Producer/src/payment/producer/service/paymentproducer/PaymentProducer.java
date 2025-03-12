package payment.producer.service.paymentproducer;

import java.util.List;

public interface PaymentProducer {
	public void createPayment(Payment payment);
	public Payment getPayment(int id);
	public void updatePayment(int id, Double amount, String paymentMethod, String status);
	public void deletePayment(int id);
	List<Payment> getAllPayments();
}
