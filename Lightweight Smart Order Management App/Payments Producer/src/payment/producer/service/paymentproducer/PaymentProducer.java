package payment.producer.service.paymentproducer;

import java.util.List;

public interface PaymentProducer {
	public void createPayment(Payment payment);
	public Payment getPayment(int id);
	public List<Payment> getPayments(int id);
	public void deletePayment(int id);
}
