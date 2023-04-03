package sia5;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

/**
 * 10.3.2 리액티브 타입 조합하기
 */
public class FluxMergingTests {

  /**
   * Flux는 가능한 빨리 데이터를 방출
   *  > delayElements 오퍼레이션을 사용해서 조금 느리게 방출
   *   > foodFlux가 다음에 방출되도록 delaySubscription 오퍼레이션 사용
   */
  @Test
  public void mergeFluxes() {

    
    Flux<String> characterFlux = Flux
        .just("Garfield", "Kojak", "Barbossa")
        .delayElements(Duration.ofMillis(500));
    Flux<String> foodFlux = Flux
        .just("Lasagna", "Lollipops", "Apples")
        .delaySubscription(Duration.ofMillis(250))
        .delayElements(Duration.ofMillis(500));

    /**
     * 두 개의 Flux 타입의 리액티브 인스턴스가 일정한 속도로 방출 되기 때문에
     * 번갈아 가면서 mergedFlux에 설정됨
     */
    Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

    StepVerifier.create(mergedFlux)
        .expectNext("Garfield")
        .expectNext("Lasagna")
        .expectNext("Kojak")
        .expectNext("Lollipops")
        .expectNext("Barbossa")
        .expectNext("Apples")
        .verifyComplete();
  }

  /**
   * zip()
   * 각 Flux 타입의 리액티브 인스턴스로부터 한 항목씩 번갈아 가져와 새로운 Flux를 생성
   */
  @Test
  public void zipFluxes() {
    Flux<String> characterFlux = Flux
        .just("Garfield", "Kojak", "Barbossa");
    Flux<String> foodFlux = Flux
        .just("Lasagna", "Lollipops", "Apples");


    Flux<Tuple2<String, String>> zippedFlux = 
        Flux.zip(characterFlux, foodFlux);
    
    StepVerifier.create(zippedFlux)
          .expectNextMatches(p -> 
              p.getT1().equals("Garfield") && 
              p.getT2().equals("Lasagna"))
          .expectNextMatches(p -> 
              p.getT1().equals("Kojak") && 
              p.getT2().equals("Lollipops"))
          .expectNextMatches(p -> 
              p.getT1().equals("Barbossa") && 
              p.getT2().equals("Apples"))
          .verifyComplete();
  }

  /**
   * zip()
   *
   * zip으로 생성되는 Tuple2 형태가 아니라
   * 우리가 원하는 객체를 생성하고 싶으면 함수를 제공하면 됨
   */
  @Test
  public void zipFluxesToObject() {
    Flux<String> characterFlux = Flux
        .just("Garfield", "Kojak", "Barbossa");
    Flux<String> foodFlux = Flux
        .just("Lasagna", "Lollipops", "Apples");

    Flux<String> zippedFlux = 
        Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);
    
    StepVerifier.create(zippedFlux)
          .expectNext("Garfield eats Lasagna")
          .expectNext("Kojak eats Lollipops")
          .expectNext("Barbossa eats Apples")
          .verifyComplete();
  }
  
  
  @Test
  public void firstFlux() {
    // delay needed to "slow down" the slow Flux
    
    Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
          .delaySubscription(Duration.ofMillis(100));
    Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");

    /**
     * 두 Flux 타입의 리액티브 인스턴스에서 먼저 방출되는 값만 취함
     */
    Flux<String> firstFlux = Flux.first(slowFlux, fastFlux);
    
    StepVerifier.create(firstFlux)
        .expectNext("hare")
        .expectNext("cheetah")
        .expectNext("squirrel")
        .verifyComplete();
  }

}
