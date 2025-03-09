package payment.producer.service.paymentproducer;

import java.sql.Connection;
import java.util.List;

public class PaymentProducerImpl implements PaymentProducer {
	
	private Connection conn;
	
	public PaymentProducerImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void createPayment(Payment payment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Payment getPayment(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getPayments(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePayment(int id) {
		// TODO Auto-generated method stub
		
	}

}
