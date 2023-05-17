package tacos.ingredientclient.webclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Configuration
/**
 * 프로파일이 webclient 일때 실행
 *  ㅁ JVM 옵션을 사용해 프로파일을 지정할 수 있음
 *    > java -jar -Dspring.profiles.active=webclient target/*.jar
 */
@Profile("webclient")
@Slf4j
public class WebClientConfig {

  /** 로드 밸런싱된 WebClient 빈을 선언
   *
   * 현재 WebClient는 리본을 통해서만 서비스를 찾는다는 것을 스프링 클라우드에 알려주고,
   * 주입 식별자로 동작(서비스 이름, HTTP 요청에 호스트와 포트 대신 사용할 수 있음)
   */
  @Bean
  @LoadBalanced
  public WebClient.Builder webClientBuilder() {
    return WebClient.builder();
  }
  
  @Bean
  public CommandLineRunner startup() {
    return args -> {
      log.info("**************************************");
      log.info("     Configuring with WebClient");
      log.info("**************************************");
    };
  }
}
