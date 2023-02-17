package tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer는 인터페이스이긴 하지만,
 * 필요한 메소드만 구현하면 되고, 아래에서는 addViewControllers만 구현
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 *
	 * @param registry 뷰 컨트롤러 등록을 위한 인자
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}
}