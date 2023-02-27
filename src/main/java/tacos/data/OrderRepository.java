package tacos.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import tacos.Order;
import tacos.User;

import java.util.List;

/**
 * 주문 정보를 저장
 */
//public interface OrderRepository {
//public interface OrderRepository
public interface OrderRepository extends CrudRepository<Order, String> {
    List<User> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
    //Order save(Order order);
}