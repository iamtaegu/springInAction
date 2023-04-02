package sia5;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

/**
 * 10.3.3 리액티브 스트림의 변환과 필터링
 */
public class FluxTransformingTests {

  @Test
  public void skipAFew() {
    // 앞에 세 개는 건너뛰고 마지막 두 항목만 발행
    Flux<String> countFlux = Flux.just(
        "one", "two", "skip a few", "ninety nine", "one hundred")
        .skip(3);
   
    StepVerifier.create(countFlux)
        .expectNext("ninety nine", "one hundred")
        .verifyComplete();
  }
  
  @Test
  public void skipAFewSeconds() {
    // 4초 동안 기다렸다가 값을 방출하는 결과 Flux를 생성
    Flux<String> countFlux = Flux.just(
        "one", "two", "skip a few", "ninety nine", "one hundred")
        .delayElements(Duration.ofSeconds(1))
        .skip(Duration.ofSeconds(4));
   
    StepVerifier.create(countFlux)
        .expectNext("ninety nine", "one hundred")
        .verifyComplete();
  }
  
  @Test
  public void take() {
    // take는 지정된 수 만큼만 방출
    Flux<String> nationalParkFlux = Flux.just(
        "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia")
        .take(3);
   
    StepVerifier.create(nationalParkFlux)
        .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
        .verifyComplete();
  }
  
  @Test
  public void takeForAwhile() {
    Flux<String> nationalParkFlux = Flux.just(
        "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
        .delayElements(Duration.ofSeconds(1))
        .take(Duration.ofMillis(3500));
   
    StepVerifier.create(nationalParkFlux)
        .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
        .verifyComplete();
  }

  /**
   * skip, take 오퍼레이션을 통해 카운트나 경과 시간을 필터 조건으로 
   * 리액티브 인스턴스의 방출 값을 선택할 수 있으나
   * 
   * 범용적인 필터링에는 filter 오퍼레이션을 사용
   */
  @Test
  public void filter() {
    Flux<String> nationalParkFlux = Flux.just(
        "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
        .filter(np -> !np.contains(" ")); //람다로 조건식 지정
   
    StepVerifier.create(nationalParkFlux)
        .expectNext("Yellowstone", "Yosemite", "Zion")
        .verifyComplete();
  }
  
  @Test
  public void distinct() {
    // 발행된적이 없는 값만 발행
    Flux<String> animalFlux = Flux.just(
        "dog", "cat", "bird", "dog", "bird", "anteater")
        .distinct();
   
    StepVerifier.create(animalFlux)
        .expectNext("dog", "cat", "bird", "anteater")
        .verifyComplete();
  }

  /**
   * 리액티브 데이터 매핑하기
   *
   * 리액티브 인스턴스에서 가장 많이 사용하는 오퍼레이션 중 하나는
   * 발행된 항목을 다른 형태나 타입으로 매핑(변환)하는 것임
   *
   * 리액터는 이런 목적의 map(), flatMap() 오퍼레이션을 제공함 
   */
  @Test
  public void map() {
    //map은 동기적(순차적 처리) 매핑을 수행
    //한 객체를(String) 다른 객체로(Player) 매핑
    Flux<Player> playerFlux = Flux
      .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
      .map(n -> {
        String[] split = n.split("\\s");
        return new Player(split[0], split[1]);
      });
    
    StepVerifier.create(playerFlux)
        .expectNext(new Player("Michael", "Jordan"))
        .expectNext(new Player("Scottie", "Pippen"))
        .expectNext(new Player("Steve", "Kerr"))
        .verifyComplete();
  }
  
  @Test
  public void flatMap() {
    //flatMap은 비동기적 매핑을 수행
    Flux<Player> playerFlux = Flux
      .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
      .flatMap(n -> Mono.just(n)
          .map(p -> {
              String[] split = p.split("\\s");
              return new Player(split[0], split[1]);
            })
          .subscribeOn(Schedulers.parallel())
              /**
               * 각 구독이 병렬 스레드로 수행되어야 한다는 것을 나타냄
               * subscribe는 리액티브 플로우를 구독 요청하고 실제로 구독하는 반면
               * subscribeOn은 구독이 동시적으로 처리됨
               *  > 우리가 사용하기 원하는 동시성 모델을 인자로 지정할 수 있고
               *  > Schedulers 클래스 메서드 중 하나를 선택
               */


        );
    
    List<Player> playerList = Arrays.asList(
        new Player("Michael", "Jordan"), 
        new Player("Scottie", "Pippen"), 
        new Player("Steve", "Kerr"));

    //flatMap은 map과 다르게 비동기적(비순차적) 매핑 처리
    StepVerifier.create(playerFlux)
        .expectNextMatches(p -> playerList.contains(p))
        .expectNextMatches(p -> playerList.contains(p))
        .expectNextMatches(p -> playerList.contains(p))
        .verifyComplete();
  }
  
  @Data
  private static class Player {
    private final String firstName;
    private final String lastName;
  }
  
}
