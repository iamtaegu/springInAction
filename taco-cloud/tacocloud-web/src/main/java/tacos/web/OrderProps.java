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
 * 홀더 클래스
 * @ConfigurationProperties를 통한 구성 속성의 올바른 주입
 * @Component 컴포넌트 스캔 대상으로 지정하고, 스프링 애플리케이션 컨텍스트 빈으로 등록하여 관리
 * OrderProps 사용이 필요한 다른 클래스에서 주입 받아 사용할 수 있음
 */
@ConfigurationProperties(prefix="taco.orders")
@Data
@Validated
public class OrderProps {
  
  @Min(value=5, message="must be between 5 and 25")
  @Max(value=25, message="must be between 5 and 25")
  private int pageSize = 20; //기본값은 20으로 되어 있지만 자동 구성 속성 값에 따라 달라짐

}
