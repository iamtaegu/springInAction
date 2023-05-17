package tacos.ingredientclient.resttemplate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Configuration
/**
 * 프로파일이 지정돼 있지 않을 때 기본적으로 RestTemplateConfig 실행
 */
@Conditional(NotFeignAndNotWebClientCondition.class)
@Slf4j
public class RestTemplateConfig {

  /** 로드 밸런싱된 RestTemplate 빈을 선언
   *
   * 현재의 RestTemplate이 리본을 통해서만 서비스를 찾는다는 것을 스프링 클라우드에 알려주고,
   * 주입 식별자로 동작(서비스 이름, HTTP 요청에 호스트와 포트 대신 사용할 수 있음)
   */
  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
  
  @Bean
  public CommandLineRunner startup() {
    return args -> {
      log.info("**************************************");
      log.info("    Configuring with RestTemplate");
      log.info("**************************************");
    };
  }
  
}
