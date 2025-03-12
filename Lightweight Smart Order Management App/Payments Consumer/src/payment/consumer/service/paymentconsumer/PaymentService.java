package payment.consumer.service.paymentconsumer;

import java.util.Scanner;

public interface PaymentService {
	void startPaymentService();
    void addPayments(Scanner scanner);
	void viewPayment();
	void listAllPayments();
	void deletePayment();
	void updatePayment();
}
