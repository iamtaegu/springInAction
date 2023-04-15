package tacos.web.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.data.TacoRepository;

public class DesignTacoControllerTest {

  /**
   * GET 요청 테스트
   * WebTestClien를 사용해서 Reactive Controller test
   */
  @Test
  public void shouldReturnRecentTacos() {
    /**
     * STEP1 Flux<Taco> 타입의 테스트 데이터를 생성 
     */
    Taco[] tacos = {
        testTaco(1L), testTaco(2L),
        testTaco(3L), testTaco(4L),
        testTaco(5L), testTaco(6L),
        testTaco(7L), testTaco(8L),
        testTaco(9L), testTaco(10L),
        testTaco(11L), testTaco(12L),
        testTaco(13L), testTaco(14L),
        testTaco(15L), testTaco(16L)};
    Flux<Taco> tacoFlux = Flux.just(tacos);

    /**
     * STEP2 TacoRepository를 DesignTacoController의 생성자에 주입하여 이 클래스의 인스턴스를 생성
     * 그리고 이 인스턴스는 bindToController의 인자로 전달되어 WebTestClient 인스턴스 생성
     */
    TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
    when(tacoRepo.findAll()).thenReturn(tacoFlux);
    
    WebTestClient testClient = WebTestClient.bindToController(
        new DesignTacoController(tacoRepo))
        .build();

    /**
     * HTTP GET 요청이 생기면 12개까지의 타코를 포함하는 JSON 페이로드가 응답에 포함되어야 함  
     *  > get().uri 의 호출은 제출 요청
     *  > exchange() 해당 요청을 제출
     *  > 연결되어 있는 DesignTacoController에 의해 처리
     *  > 페이로드: JSON으로 직렬화된 형태의 Taco 목록
     */
    testClient.get().uri("/design/recent")
      .exchange()
      .expectStatus().isOk()
      .expectBody()
        .jsonPath("$").isArray()
        .jsonPath("$").isNotEmpty()
        .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
        .jsonPath("$[0].name").isEqualTo("Taco 1")
        .jsonPath("$[1].id").isEqualTo(tacos[1].getId().toString())
        .jsonPath("$[1].name").isEqualTo("Taco 2")
        .jsonPath("$[11].id").isEqualTo(tacos[11].getId().toString())
        .jsonPath("$[11].name").isEqualTo("Taco 12")
        .jsonPath("$[12]").doesNotExist(); // 인덱스 0부터 시작하기 때문에 존재하면 안됨
  }

  /**
   * POST 요청 테스트
   * API의 타코 생성 엔드포인트를 테스트
   */
  @Test
  public void shouldSaveATaco() {
    TacoRepository tacoRepo = Mockito.mock(
                TacoRepository.class);
    Mono<Taco> unsavedTacoMono = Mono.just(testTaco(null));
    Taco savedTaco = testTaco(null);
    Mono<Taco> savedTacoMono = Mono.just(savedTaco);
    
    when(tacoRepo.save(any())).thenReturn(savedTacoMono);
    
    WebTestClient testClient = WebTestClient.bindToController(
        new DesignTacoController(tacoRepo)).build();
    
    testClient.post()
        .uri("/design")
        .contentType(MediaType.APPLICATION_JSON)
        .body(unsavedTacoMono, Taco.class)
      .exchange()
      .expectStatus().isCreated()
      .expectBody(Taco.class)
        .isEqualTo(savedTaco);
  }


  /**
   * Flux가 발생하는 Taco 객체 생성
   * 인자로 받은 숫자로 ID와 이름을 갖는 Taco 객체를 생성
   *  > 간단하게 모든 테스트 타코는 동일한 두 개의 식자재 객체를 갖도록 
   *  > 타코의 ID와 이름은 인자로 받은 숫자로 결정 
   * @param number
   * @return
   */
  private Taco testTaco(Long number) {
    Taco taco = new Taco();
    taco.setId(number != null ? number.toString(): "TESTID");
    taco.setName("Taco " + number);
    List<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(
        new Ingredient("INGA", "Ingredient A", Type.WRAP));
    ingredients.add(
        new Ingredient("INGB", "Ingredient B", Type.PROTEIN));
    taco.setIngredients(ingredients);
    return taco;
  }
}
