package sia5;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

/**
 * 리액티브 스트림의 데이터 버퍼링(364p)
 */
public class FluxBufferingTests {

  @Test
  public void buffer() {
    Flux<String> fruitFlux = Flux.just(
        "apple", "orange", "banana", "kiwi", "strawberry");
    
    Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);
    
    StepVerifier
        .create(bufferedFlux)
        .expectNext(Arrays.asList("apple", "orange", "banana"))
        .expectNext(Arrays.asList("kiwi", "strawberry"))
        .verifyComplete();
  }

  /**
   * 위에서 Flux 타입의 리액티브 인스턴스로부터 리액티브가 아닌 List 컬렉션으로 버펑링되는 값은 비생산적으로 보임
   * buffer()를 flatMap()과 같이 사용하면 각 List 컬렉션을 병행 처리할 수 있음
   */
  @Test
  public void bufferAndFlatMap() throws Exception {
    Flux.just(
        "apple", "orange", "banana", "kiwi", "strawberry ")
        .buffer(3)
        .flatMap(x -> 
          Flux.fromIterable(x)
            .map(y -> y.toUpperCase())
            .subscribeOn(Schedulers.parallel())   
            .log() // log
        ).subscribe();

  }
  
  @Test
  public void collectList() {
    Flux<String> fruitFlux = Flux.just(
        "apple", "orange", "banana", "kiwi", "strawberry");

    // List발행하는 Flux 대신 Mono를 생성함
    Mono<List<String>> fruitListMono = fruitFlux.collectList();
    
    StepVerifier
        .create(fruitListMono)
        .expectNext(Arrays.asList(
            "apple", "orange", "banana", "kiwi", "strawberry"))
        .verifyComplete();
  }
  
  @Test
  public void collectMap() {
    Flux<String> animalFlux = Flux.just(
        "aardvark", "elephant", "koala", "eagle", "kangaroo");

    //지정된 함수로 산출된 키를 갖는 항목이 저장됨
    Mono<Map<Character, String>> animalMapMono = 
        animalFlux.collectMap(a -> a.charAt(0));
    
    StepVerifier
        .create(animalMapMono)
        .expectNextMatches(map -> {
          return
              map.size() == 3 &&
              map.get('a').equals("aardvark") &&
              map.get('e').equals("eagle") &&
              map.get('k').equals("kangaroo");
        })
        .verifyComplete();
  }

  /**
   * 10.3.4 리액티브 타입에 로직 오퍼레이션 수행
   *
   * 리액티브 인스턴스에서 발행한 항목에 조건 로직 수행
   */
  @Test
  public void all() {
    Flux<String> animalFlux = Flux.just(
        "aardvark", "elephant", "koala", "eagle", "kangaroo");

    //모든 메시지가 조건을 충족하는지 확인
    Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));
    StepVerifier.create(hasAMono)
      .expectNext(true)
      .verifyComplete();
    
    Mono<Boolean> hasKMono = animalFlux.all(a -> a.contains("k"));
    StepVerifier.create(hasKMono)
      .expectNext(false)
      .verifyComplete();
  }
  
  @Test
  public void any() {
    Flux<String> animalFlux = Flux.just(
        "aardvark", "elephant", "koala", "eagle", "kangaroo");
    
    //최소 하나의 메시지가 조건을 충족하는지 확인
    Mono<Boolean> hasAMono = animalFlux.any(a -> a.contains("t"));
    
    StepVerifier.create(hasAMono)
      .expectNext(true)
      .verifyComplete();
    
    Mono<Boolean> hasZMono = animalFlux.any(a -> a.contains("z"));
    StepVerifier.create(hasZMono)
      .expectNext(false)
      .verifyComplete();
  }
  
}
