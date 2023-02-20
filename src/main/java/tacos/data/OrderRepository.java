package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Order;

/**
 * 주문 정보를 저장
 */
//public interface OrderRepository {
//public interface OrderRepository
public interface OrderRepository extends CrudRepository<Order, String> {
	//Order save(Order order);
}