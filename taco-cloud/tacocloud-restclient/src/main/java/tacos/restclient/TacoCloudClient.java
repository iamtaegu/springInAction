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

  // RestTemplate, Traverson DI
  public TacoCloudClient(RestTemplate rest, Traverson traverson) {
    this.rest = rest;
    this.traverson = traverson;
  }

  //
  // GET examples
  //

  /**
   * 두 번째 매겨변수는 응답 데이터 타입
   * JSON 직렬화 응답을 Ingredient 객체 타입으로 역직렬화 해서 반환
   * @param ingredientId
   * @return
   */
  public Ingredient getIngredientById(String ingredientId) {
    return rest.getForObject("http://localhost:8080/ingredients/{id}", 
                             Ingredient.class, ingredientId);
  }

  /**
   * URI 매개변수를 Map 형태로 전달할 수 있음
   * 매개변수 값은 Map key와 매칭
   * @param ingredientId
   * @return
   */
  public Ingredient getIngredientById2(String ingredientId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", ingredientId);
    return rest.getForObject("http://localhost:8080/ingredients/{id}",
        Ingredient.class, urlVariables);  
  }

  /**
   * URI 객체를 생성해서 getForObject 호출 할 수 있음
   * @param ingredientId
   * @return
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
   * HTTP response body 외에
   * HTTP response 내용 전체가 필요하면
   * getForEntity
   * @param ingredientId
   * @return
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

  /**
   * HTTP PUT
   * @param ingredient
   */
  
  public void updateIngredient(Ingredient ingredient) {
    rest.put("http://localhost:8080/ingredients/{id}",
          ingredient, ingredient.getId());
  }

  /**
   * 두 번째 매개변수는 전달 객체
   * 세 번째 매겨변수는 응답 데이터 타입
   * @param ingredient
   * @return
   */
  public Ingredient createIngredient(Ingredient ingredient) {
    return rest.postForObject("http://localhost:8080/ingredients",
        ingredient, Ingredient.class);
  }

  /**
   * 전달한 객체에 접근할 수 있는 URI 반환
   * @param ingredient
   * @return
   */
  public URI createIngredient2(Ingredient ingredient) {
    return rest.postForLocation("http://localhost:8080/ingredients",
        ingredient, Ingredient.class);
  }

  /**
   * HTTP response 전체 내용 반환
   * postForEntity
   * @param ingredient
   * @return
   */
  public Ingredient createIngredient3(Ingredient ingredient) {
    ResponseEntity<Ingredient> responseEntity =
           rest.postForEntity("http://localhost:8080/ingredients",
                              ingredient,
                              Ingredient.class);
    log.info("New resource created at " +
             responseEntity.getHeaders().getLocation());
    return responseEntity.getBody();
  }

  /**
   * HTTP delete
   * @param ingredient
   */
  
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
