package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 아래와 같이 WebConfig를 따로 분리하지 않고
 * 부트스트랩 클래스에 인바이딩 시켜줄 수 있음
 * 하지만 구성 클래스는 필요하기 때문에 
 * 구성 종류(웹, 데이터 ,보안)에 따라 클래스로 분리하는게 나음
 */
@SpringBootApplication
public class TacoCloudApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/bootstrapViewController").setViewName("home");
	}
}
