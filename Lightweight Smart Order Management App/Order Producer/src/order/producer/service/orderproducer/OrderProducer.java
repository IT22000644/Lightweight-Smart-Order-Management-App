package order.producer.service.orderproducer;

import java.util.List;

public interface OrderProducer {
	public void createOrder(Order order);
	public Order getOrder(int id);
	public List<Order> getOrders();
	public boolean deleteOrder(int id);
}
