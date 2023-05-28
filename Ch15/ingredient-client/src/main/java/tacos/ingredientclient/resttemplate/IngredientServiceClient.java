package tacos.ingredientclient.resttemplate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import tacos.ingredientclient.Ingredient;

@Service
@Conditional(NotFeignAndNotWebClientCondition.class)
public class IngredientServiceClient {

  private RestTemplate rest;
  
  public IngredientServiceClient(@LoadBalanced RestTemplate rest) {
    this.rest = rest;
  }
  
  @HystrixCommand(fallbackMethod="getDefaultIngredientDetails")
  public Ingredient getIngredientById(String ingredientId) {
    return rest.getForObject(
        "http://ingredient-service/ingredients/{id}", 
        Ingredient.class, ingredientId);
  }
  
  private Ingredient getDefaultIngredientDetails(String ingredientId) {
    if (ingredientId.equals("FLTO")) {
      return new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
    } else if (ingredientId.equals("GRBF")) {
      return new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
    } else {
      return new Ingredient("CHED", "Shredded Cheddar", Ingredient.Type.CHEESE);
    }
  }

  /**
   * HystrixCommand()가 지정된 모든 메서드는 1초 후에 타임아웃 처리 됨
   *          > 타임아웃 되면 fallbackMethod 실행
   */
  @HystrixCommand(fallbackMethod="getDefaultIngredients",
      commandProperties={
          /**
           * 20초 이내에 메서드가 30번이상 호출되어 25% 이상 실패일 경우
           * 절반-열림 상태가 되기 전에 1분까지 열림 상태에 머무름(sleepWindowInMilliseconds)
           */
          @HystrixProperty(
              name="execution.isolation.thread.timeoutInMilliseconds",
              value="500"),
              @HystrixProperty(
                  // 지정된 시간 내에 메서드가 호출되어야 하는 횟수
                  name="circuitBreaker.requestVolumeThreshold",
                  value="30"),
              @HystrixProperty(
                  // 지정된 시간 내에 실패한 메서드 호출의 비율
                  name="circuitBreaker.errorThresholdPercentage",
                  value="25"),
              @HystrixProperty(
                  // 요청 횟수와 에러 비율이 고려되는 시간
                  name="metrics.rollingStats.timeInMilliseconds",
                  value="20000"),
              @HystrixProperty(
                  // 절반-열림 상태로 진입하여 실패한 메서드가 다시 시도되기 전에 열림 상태의 서킷이 유지되는 시간
                  name="circuitBreaker.sleepWindowInMilliseconds",
                  value="60000")
      })

  /**
   * 메서드 내용 : RestTemplate를 사용해 식자재 서비스로부터 Ingredient 객체들이 저장된 컬렉션을 가져옴
   * 서킷 브레이커 대상 : exchange()
   *                  > 유레카에 ingredient-service로 등록된 서비스가 없으면 RestClientException이 발생
   *                  > try/catch 블록으로 처리하지 않았기 때문에 getAllIngredients() 호출자에 RestClientException throws 됨
   * 서킷 브레이커 처리 : @HystrixCommand(fallbackMethod="getDefaultIngredients")
   *                  > unchecked 예외가 발생해 getAllIngredients()를 벗어나면
   *                  > 서킷 브레이커가 해당 예외를 잡아서 폴백 메서드를 호출해 줌
   * 폴백 메서드 : 원래 의도했던 메서드가 실행 불가능할 때를 대비
   *                  > 원래의 메서드와 시그니처가 같아야 함(메서드 이름만 달라야 함)
   */
  public Iterable<Ingredient> getAllIngredients() {
    ParameterizedTypeReference<List<Ingredient>> stringList =
        new ParameterizedTypeReference<List<Ingredient>>() {};
    return rest.exchange(
        "http://ingredient-service/ingredients", HttpMethod.GET,
        HttpEntity.EMPTY, stringList).getBody();
  }

  private Iterable<Ingredient> getDefaultIngredients() {
    List<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(new Ingredient(
            "FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
    ingredients.add(new Ingredient(
            "GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
    ingredients.add(new Ingredient(
            "CHED", "Shredded Cheddar", Ingredient.Type.CHEESE));
    return ingredients;
  }
  
}
