package tacos.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 구성 속성 홀드 빈
 * 사용으로 속성 관련 코드를 한 곳에 모아둘 수 있음
 */
@Component
@ConfigurationProperties(prefix="taco.orders")
@Data
public class OrderProps {

    @Min(value =5, message = "must be between 5 and 25")
    @Max(value =25, message = "must be between 5 and 25")
    int pageSize = 20;
}
