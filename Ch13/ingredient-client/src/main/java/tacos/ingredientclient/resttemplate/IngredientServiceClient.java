package tacos.ingredientclient.resttemplate;

import java.util.Arrays;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tacos.ingredientclient.Ingredient;

@Service
@Conditional(NotFeignAndNotWebClientCondition.class)
public class IngredientServiceClient {

  private RestTemplate rest;
  // 로드 밸런싱된 RestTemplate 빈을 주입
  public IngredientServiceClient(@LoadBalanced RestTemplate rest) {
    this.rest = rest;
  }

  /**
   * 내부적으로 서비스 인스턴스를 선택하도록 RestTemplate 리본에 요청
   * 선택된 서비스 인스턴스의 호스트와 포트 정보를 포함하도록
   * 리본이 URL을 변경한 후 원래대로 RestTemplate 사용
   */
  public Ingredient getIngredientById(String ingredientId) {
    return rest.getForObject(
        "http://ingredient-service/ingredients/{id}", 
        Ingredient.class, ingredientId);
  }
  
  public Iterable<Ingredient> getAllIngredients() {
    Ingredient[] ingredients = rest.getForObject(
        "http://ingredient-service/ingredients", Ingredient[].class);
    return Arrays.asList(ingredients);
  }
  
}
