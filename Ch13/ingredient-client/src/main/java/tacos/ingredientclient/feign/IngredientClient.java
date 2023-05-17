package tacos.ingredientclient.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tacos.ingredientclient.Ingredient;

/**
 * 구현 코드가 없는 인터페이스 형태 이지만,
 * 애플리케이션 런타임 시점에 Fegin이 구현 클래스를 생성하고,
 * 스프링 애플리케이션 컨텍스트에 빈을 노출 시킴
 */
@FeignClient("ingredient-service") // ingredient-service 리본
public interface IngredientClient {

  @GetMapping("/ingredients/{id}")
  Ingredient getIngredient(@PathVariable("id") String id);

  @GetMapping("/ingredients")
  Iterable<Ingredient> getAllIngredients();

}
