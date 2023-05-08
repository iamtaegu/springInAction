package tacos.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import tacos.Taco;

/**
 * ReactiveCrudRepository - 리액티브 리퍼지터리
 * ReactiveMongoRepository - 몽고DB 전용 리액티브 리퍼지터리
 */
public interface TacoRepository 
         extends ReactiveCrudRepository<Taco, String> {

}
