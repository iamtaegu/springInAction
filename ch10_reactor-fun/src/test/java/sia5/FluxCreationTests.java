package sia5;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxCreationTests {

  @Test
	public void createAFlux_just() {
	  //5개 스트링 객체로부터 Flux 객체 생성

    Flux<String> fruitFlux = Flux
	      .just("Apple", "Orange", "Grape", "Banana", "Strawberry");
	  /**
	   * subscriber(구독자), StepVerifier
	   * Flux 타입의 리액티브 인스턴스를 구독하고
	   * 스트림을 통해 전달되는 데이터에 assersion을 적용해줌
	   * 그리고 기대하는 데이터가 전달됐는지 검사해줌
	   */
    StepVerifier.create(fruitFlux)
        .expectNext("Apple")
        .expectNext("Orange")
        .expectNext("Grape")
        .expectNext("Banana")
        .expectNext("Strawberry")
        .verifyComplete();
	}

	/**
	 * 컬렉션으로부터 Flux 타입의 리액티브 인스턴스 생성
	 * 	>컬렉션, String List
	 */
	@Test
	public void createAFlux_fromArray() {
	  String[] fruits = new String[] {
	      "Apple", "Orange", "Grape", "Banana", "Strawberry" };
	  
    Flux<String> fruitFlux = Flux.fromArray(fruits);
    
    StepVerifier.create(fruitFlux)
        .expectNext("Apple")
        .expectNext("Orange")
        .expectNext("Grape")
        .expectNext("Banana")
        .expectNext("Strawberry")
        .verifyComplete();
	}

	/**
	 * 컬렉션으로부터 Flux 타입의 리액티브 인스턴스 생성
	 * 	>컬렉션, Iterable subClass
	 */
	@Test
	public void createAFlux_fromIterable() {
	  List<String> fruitList = new ArrayList<>();
	  fruitList.add("Apple");
	  fruitList.add("Orange");
	  fruitList.add("Grape");
    fruitList.add("Banana");
	  fruitList.add("Strawberry");
	  
	  Flux<String> fruitFlux = Flux.fromIterable(fruitList);
	  
    StepVerifier.create(fruitFlux)
        .expectNext("Apple")
        .expectNext("Orange")
        .expectNext("Grape")
        .expectNext("Banana")
        .expectNext("Strawberry")
        .verifyComplete();
	}

	/**
	 * Stream 객체로부터 Flux 타입의 리액티브 인스턴스 생성
	 */
	 @Test
	 public void createAFlux_fromStream() {
	   Stream<String> fruitStream = 
	        Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
	    
	   Flux<String> fruitFlux = Flux.fromStream(fruitStream);
	    
	   StepVerifier.create(fruitFlux)
	       .expectNext("Apple")
	       .expectNext("Orange")
	       .expectNext("Grape")
	       .expectNext("Banana")
	       .expectNext("Strawberry")
	       .verifyComplete();
	 }

	/**
	 * Counter 역할의 Flux
	 */
	@Test
	 public void createAFlux_interval() {
	   Flux<Long> intervalFlux = 
	       Flux.interval(Duration.ofSeconds(1))
	           .take(5);
	   //0부터 시작하여 4까지 증가
     StepVerifier.create(intervalFlux)
         .expectNext(0L)
         .expectNext(1L)
         .expectNext(2L)
         .expectNext(3L)
         .expectNext(4L)
         .verifyComplete();
	 }
	 
   @Test
   public void createAFlux_range() {
     Flux<Integer> intervalFlux = 
         Flux.range(1, 5);
     
     StepVerifier.create(intervalFlux)
         .expectNext(1)
         .expectNext(2)
         .expectNext(3)
         .expectNext(4)
         .expectNext(5)
         .verifyComplete();
   }
}
