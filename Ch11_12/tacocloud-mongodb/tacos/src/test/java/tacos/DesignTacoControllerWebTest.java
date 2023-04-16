package tacos;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * WebTestClient의 통합 테스트를 작성하기 위해서는 다른 스프링 부트 통합 테스트처럼
 * 아래 두 개 애노테이션을 지정하는 것부터 시작
 *  > RANDOM_PORT, 무작위로 선택된 포트로 실행 서버가 리스닝하도록 스프링에 요청
 * 스프링이 DesignTacoController의 인스턴스를 생성하고 실제 TacoRepository를 주입해줌
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class DesignTacoControllerWebTest {

  @Autowired
  private WebTestClient testClient;

  /**
   * get().uri() 의 오출은 제출 요청
   * exchage() 해당 요청을 제출
   * @throws IOException
   */
  @Test
  public void shouldReturnRecentTacos() throws IOException {
    testClient.get().uri("/design/recent")
      .accept(MediaType.APPLICATION_JSON).exchange()
      .expectStatus().isOk()
      .expectBody()
          .jsonPath("$[?(@.id == 'TACO1')].name")
              .isEqualTo("Carnivore")
          .jsonPath("$[?(@.id == 'TACO2')].name")
              .isEqualTo("Bovine Bounty")
          .jsonPath("$[?(@.id == 'TACO3')].name")
              .isEqualTo("Veg-Out");
  }

}
