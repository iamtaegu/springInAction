package tacos.restclient;

import java.util.Collection;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Taco;

@Service
@Slf4j
public class TacoCloudClient {

  private RestTemplate rest;
  private Traverson traverson;

  /**
   * RestTemplate는 RestExamples에서 @Bean으로 선언받아 아래 생성자 주입 받아 사용 중
   * @param rest
   * @param traverson
   */
  public TacoCloudClient(RestTemplate rest, Traverson traverson) {
    this.rest = rest;
    this.traverson = traverson;
  }

  //
  // GET examples
  //

  /**
   * 리소스 가져오기(GET)
   * getForObject param2는 응답이 바인딩 되는 타입
   *  > 여기서는 JSON 형식의 응답 데이터가 Ingredient로 역직렬화 되어 반환 됨
   *
   * 매개변수를 사용하기 위해서는 URI 객체를 구성하여 getForObject 호출
   * getForObject 대신 getForEntity를 사용하면 ResponseEntity를 반환받을 수 있음
   * @param ingredientId
   * @return
   */
  public Ingredient getIngredientById(String ingredientId) {
    return rest.getForObject("http://localhost:8080/ingredients/{id}",
                             Ingredient.class, ingredientId);
  }

  /*
   * Alternate implementations...
   * The next three methods are alternative implementations of
   * getIngredientById() as shown in chapter 6. If you'd like to try
   * any of them out, comment out the previous method and uncomment
   * the variant you want to use.
   */

  /*
   * Specify parameters with a map
   */
  // public Ingredient getIngredientById(String ingredientId) {
  //   Map<String, String> urlVariables = new HashMap<>();
  //   urlVariables.put("id", ingredientId);
  //   return rest.getForObject("http://localhost:8080/ingredients/{id}",
  //       Ingredient.class, urlVariables);
  // }

  /*
   * Request with URI instead of String
   */
  // public Ingredient getIngredientById(String ingredientId) {
  //   Map<String, String> urlVariables = new HashMap<>();
  //   urlVariables.put("id", ingredientId);
  //   URI url = UriComponentsBuilder
  //             .fromHttpUrl("http://localhost:8080/ingredients/{id}")
  //             .build(urlVariables);
  //   return rest.getForObject(url, Ingredient.class);
  // }

  /*
   * Use getForEntity() instead of getForObject()
   */
  // public Ingredient getIngredientById(String ingredientId) {
  //   ResponseEntity<Ingredient> responseEntity =
  //       rest.getForEntity("http://localhost:8080/ingredients/{id}",
  //           Ingredient.class, ingredientId);
  //   log.info("Fetched time: " +
  //           responseEntity.getHeaders().getDate());
  //   return responseEntity.getBody();
  // }

  public List<Ingredient> getAllIngredients() {
    return rest.exchange("http://localhost:8080/ingredients",
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {})
        .getBody();
  }

  /**
   * 리소스 쓰기(PUT)
   * @param ingredient
   */
  public void updateIngredient(Ingredient ingredient) {
    rest.put("http://localhost:8080/ingredients/{id}",
          ingredient, ingredient.getId());
  }

  /**
   * 리소스 추가하기(POST)
   * postForObject 사용하여 새로 생성한 Ingredient를 반환 받음
   * postForLocation 새로 생성한 Ingredient URI 반환
   * postForEntity ReponseEntity 반환 
   * @param ingredient
   * @return
   */
  public Ingredient createIngredient(Ingredient ingredient) {
    return rest.postForObject("http://localhost:8080/ingredients",
        ingredient, Ingredient.class);
  }

  /*
   * Alternate implementations...
   * The next two methods are alternative implementations of
   * createIngredient() as shown in chapter 6. If you'd like to try
   * any of them out, comment out the previous method and uncomment
   * the variant you want to use.
   */

  // public URI createIngredient(Ingredient ingredient) {
  //   return rest.postForLocation("http://localhost:8080/ingredients",
  //       ingredient, Ingredient.class);
  // }

  // public Ingredient createIngredient(Ingredient ingredient) {
  //   ResponseEntity<Ingredient> responseEntity =
  //          rest.postForEntity("http://localhost:8080/ingredients",
  //                             ingredient,
  //                             Ingredient.class);
  //   log.info("New resource created at " +
  //            responseEntity.getHeaders().getLocation());
  //   return responseEntity.getBody();
  // }

  /**
   * 리소스 삭제하기(delete)
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
    
    Collection<Ingredient> ingredients = ingredientRes.getContent();
          
    return ingredients;
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

  public Iterable<Taco> getRecentTacosWithTraverson() {
    ParameterizedTypeReference<Resources<Taco>> tacoType =
        new ParameterizedTypeReference<Resources<Taco>>() {};

    Resources<Taco> tacoRes =
        traverson
          .follow("tacos")
          .follow("recents")
          .toObject(tacoType);

      // Alternatively, list the two paths in the same call to follow()
//    Resources<Taco> tacoRes =
//        traverson
//          .follow("tacos", "recents")
//          .toObject(tacoType);

    return tacoRes.getContent();
  }

}
