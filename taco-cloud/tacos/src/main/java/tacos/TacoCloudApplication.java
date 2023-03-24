package tacos;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 스프링 부트 애플리케이션
 * 아래 세 개 애노테이션을 결합 했음
 *  ㅁ @SpringBootConfiguration, 구성 클래스 지정
 *  ㅁ @EnableAutoConfiguration, 스프링부트 자동-구성 활성화
 *  ㅁ @ComponentScan, 컴포넌트 검색 활성화
 */
@SpringBootApplication
public class TacoCloudApplication {

  public static void main(String[] args) {
    SpringApplication.run(TacoCloudApplication.class, args); //애플리케이션 실행
  }

  // To avoid 404s when using Angular HTML 5 routing
  @Bean
  ErrorViewResolver supportPathBasedLocationStrategyWithoutHashes() {
      return new ErrorViewResolver() {
          @Override
          public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
              return status == HttpStatus.NOT_FOUND
                      ? new ModelAndView("index.html", Collections.<String, Object>emptyMap(), HttpStatus.OK)
                      : null;
          }
      };
  }

}
