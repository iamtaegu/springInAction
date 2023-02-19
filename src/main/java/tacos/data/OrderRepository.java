package tacos.data;
import tacos.Order;

/**
 * 주문 정보를 저장
 */
public interface OrderRepository {
	Order save(Order order);
}