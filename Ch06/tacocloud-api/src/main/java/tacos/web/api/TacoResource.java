package tacos.web.api;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;
import tacos.Taco;

// 6.2.3 embedded 관계 이름 짓기
/**
 * @Relation을 추가하여 스프링 HATEOAS가 결과 JSON의 필드 이름을 짓는 방법을 지정
 * 이렇게 함으로써 자바로 정의된 리소스 타입 클래스 이름(taco)과 JSON 필드 이름 간(tacos)의 결합도를 낮출 수 있음
 */
@Relation(value="taco", collectionRelation="tacos")
public class TacoResource extends ResourceSupport {

  private static final IngredientResourceAssembler 
            ingredientAssembler = new IngredientResourceAssembler();
  
  @Getter
  private final String name;

  @Getter
  private final Date createdAt;

  @Getter
  private final List<IngredientResource> ingredients;
  
  public TacoResource(Taco taco) {
    this.name = taco.getName();
    this.createdAt = taco.getCreatedAt();
    this.ingredients = 
        ingredientAssembler.toResources(taco.getIngredients());
  }
  
}
