package tacos.data;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;
import tacos.User;

/**
 * 아래 메서드는 스프링 데이터 명명 구칙을 따라 정의했고
 * 반환 타입만 리액터(Mono)로 변환하여 사용
 */
public interface UserRepository extends ReactiveCrudRepository<User, String> {

  Mono<User> findByUsername(String username);
  
  Mono<User> findByEmail(String email);
  
}
