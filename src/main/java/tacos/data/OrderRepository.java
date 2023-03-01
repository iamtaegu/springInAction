package tacos.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tacos.Order;
import tacos.Taco;
import tacos.User;

import java.util.List;

/**
 * 주문 정보를 저장
 */
//public interface OrderRepository {
//public interface OrderRepository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    List<User> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
    //Order save(Order order);
}