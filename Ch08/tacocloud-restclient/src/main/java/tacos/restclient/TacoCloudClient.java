package tacos.restclient;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;

@Service
@Slf4j
public class TacoCloudClient {

  private RestTemplate rest;
  private Traverson traverson;

  public TacoCloudClient(RestTemplate rest, Traverson traverson) {
    this.rest = rest;
    this.traverson = traverson;
  }

  /**
   * RestTemplate.getForObject - GET 요청을 전송하고, 응답 몸체와 연결되는 객체를 반환
   *  ㅁ 아래 예제에서는 하나의 변수만 사용했지만
   *  ㅁ 주어진 매개변수들의 순서대로 플레이스 홀더에 지정됨
   * @param ingredientId
   * @return
   */
  public Ingredient getIngredientById(String ingredientId) {
    return rest.getForObject("http://localhost:8080/ingredients/{id}", 
                             Ingredient.class, ingredientId);
  }

  /**
   * RestTemplate.getForObject - GET
   *  ㅁ Map 형태 파라미터 사용
   */
  public Ingredient getIngredientById2(String ingredientId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", ingredientId);
    return rest.getForObject("http://localhost:8080/ingredients/{id}",
        Ingredient.class, urlVariables);  
  }

  /**
   * RestTemplate.getForObject - GET
   *  ㅁ URI 쿼리스트링 사용
   */
  public Ingredient getIngredientById3(String ingredientId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", ingredientId);
    URI url = UriComponentsBuilder
              .fromHttpUrl("http://localhost:8080/ingredients/{id}")
              .build(urlVariables);
    return rest.getForObject(url, Ingredient.class);
  }

  /**
   * RestTemplate.getForEntity - GET
   *  ㅁ 응답 결과인 도메인 객체와
   *  ㅁ 응답 헤더와 같은 더 상세한 응답 콘테츠가 포함
   *  ㅁ 주어진 매개변수들의 순서대로 플레이스 홀더에 지정
   */
  public Ingredient getIngredientById4(String ingredientId) {
    ResponseEntity<Ingredient> responseEntity =
        rest.getForEntity("http://localhost:8080/ingredients/{id}",
            Ingredient.class, ingredientId);
    log.info("Fetched time: " +
            responseEntity.getHeaders().getDate());
    return responseEntity.getBody();
  }
  
  public List<Ingredient> getAllIngredients() {
    return rest.exchange("http://localhost:8080/ingredients", 
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {})
        .getBody();
  }
  
  //
  // PUT examples
  //
  public void updateIngredient(Ingredient ingredient) {
    rest.put("http://localhost:8080/ingredients/{id}",
          ingredient, ingredient.getId());
  }
  
  //
  // POST examples
  //
  public Ingredient createIngredient(Ingredient ingredient) {
    return rest.postForObject("http://localhost:8080/ingredients",
        ingredient, Ingredient.class);
  }

  /**
   * 새로 생성된 리소스의 위치까지 반환
   *  ㅁ 리소스 대신 새로 생성된 리소스의 URI를 반환
   *  ㅁ 새로 생성된 리소스 도메인 객체와 리소스 위치(URI)가 필요하면
   *    > postForEntity() 사용 - createIngredient3
   * @param ingredient
   * @return
   */
  public URI createIngredient2(Ingredient ingredient) {
    return rest.postForLocation("http://localhost:8080/ingredients",
        ingredient, Ingredient.class);
  }
  
  public Ingredient createIngredient3(Ingredient ingredient) {
    ResponseEntity<Ingredient> responseEntity =
           rest.postForEntity("http://localhost:8080/ingredients",
                              ingredient,
                              Ingredient.class);
    log.info("New resource created at " +
             responseEntity.getHeaders().getLocation());
    return responseEntity.getBody();
  }
  
  //
  // DELETE examples
  //
  
  public void deleteIngredient(Ingredient ingredient) {
    rest.delete("http://localhost:8080/ingredients/{id}",
        ingredient.getId());
  }
  
  //
  // Traverson with RestTemplate examples
  //
  
  public Iterable<Ingredient> getAllIngredientsWithTraverson() {
    ParameterizedTypeReference<Resources<Ingredient>> ingredientType =
        new ParameterizedTypeReference<Resources<Ingredient>>() {};
    Resources<Ingredient> ingredientRes =
        traverson
          .follow("ingredients")
          .toObject(ingredientType);
    return ingredientRes.getContent();
  }
  
  public Ingredient addIngredient(Ingredient ingredient) {
    String ingredientsUrl = traverson
        .follow("ingredients")
        .asLink()
        .getHref();
    return rest.postForObject(ingredientsUrl,
                              ingredient,
                              Ingredient.class);
  }
  
}
