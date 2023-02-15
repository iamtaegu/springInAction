package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //스프링부트 애플리케이션
public class TacoCloudApplication {

	public static void main(String[] args) {
		//스프링 애플리케이션 컨텍스트 생성
		SpringApplication.run(TacoCloudApplication.class, args);
	}

}
