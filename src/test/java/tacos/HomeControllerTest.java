package tacos;
import static org.hamcrest.Matchers.containsString;
import static
	org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static
	org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static
	org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static
	org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * HomeController의 웹 페이지 테스트
 * @SprintBootTest 대신 @WebMvcTest 애노테이션 사용에 주목
 * 스프링Mvc 형태로 테스트 실행 지원
 */
@WebMvcTest(HomeController.class)
public class HomeControllerTest {
	@Autowired
	private MockMvc mockMvc;//MockMvc 주입
	@Test
	public void testHomePage() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isOk()) //HTTP GET 요청 수행이 성공적인지 
		.andExpect(view().name("home"))//뷰 이름이 home이 맞는지
		.andExpect(content().string(
				containsString("Welcome to...")));//해당 문구를 포함하는지 확인
	}
}
