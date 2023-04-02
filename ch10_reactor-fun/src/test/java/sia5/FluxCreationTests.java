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
	public void createFlux_just() {

		Flux<String> fruitFlux = Flux.just("test1", "test2");

		fruitFlux.subscribe( // 구독자를 추가하는 즉시 데이터는 방출 됨
			f -> System.out.println(f)
		);

	}

	@Test
	public void createAFlux_just() {

	  /**
	   * just() 메서드(static, 클래스 메서드)를 사용하여
	   * 5개 스트링 객체로부터 리액티브 타입, Flux 객체 생성
	   */
		Flux<String> fruitFlux = Flux
			  .just("Apple", "Orange", "Grape", "Banana", "Strawberry");
		  /**
		   * subscriber(구독자), StepVerifier
		   * Flux 타입의 리액티브 인스턴스를 구독하고
		   * 스트림을 통해 전달되는 데이터에 assersion을 적용해줌
		   * 	> fruitFLux가 방출한 값들
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
	 * 컬렉션으로부터 생성하기
	 *  > 배열, Iterable 객체, Stream 객체로부터 생성할 수 있음
	 *
	 * 배열로부터 Flux 타입의 리액티브 인스턴스 생성
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
	 * 	> Iterable의 구현 컬렉션인 경우 FLux.fromIterable
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
	 * 데이터 없이 새 값으로 증가하는 숫자를 방출하는 
	 * 카운터 역할의 리액티브 인스턴스 생
	 * 
	 * Flux 타입의 카운터 리액티브 인스턴스 생성
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
