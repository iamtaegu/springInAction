package tacos.web;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.
                                        ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Component
/**
 * 우리의 구성 속성 사용하기
 * application.yml 에 정의되어 있음
 */

@ConfigurationProperties(prefix="taco.orders")
@Data
@Validated
public class OrderProps {
  
  @Min(value=5, message="must be between 5 and 25")
  @Max(value=25, message="must be between 5 and 25")
  private int pageSize = 20;

}
