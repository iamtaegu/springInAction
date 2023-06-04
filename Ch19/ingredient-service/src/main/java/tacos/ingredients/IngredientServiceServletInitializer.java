/*
 * Note that this class is placed here as a representation of listing 19.1
 * in the book. It is, however, dead weight unless you also change the pom.xml
 * file to produce a WAR file instead of a JAR file.
 */
package tacos.ingredients;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class IngredientServiceServletInitializer 
       extends SpringBootServletInitializer {

  /**
   * 종전 web.xml에서 WAS에게 DispatcherServlet을 알려주던 것을
   * 스프링에서는 SpringBootServletInitializer.configure을 통해 지정해줄 수 있음
   */
  @Override
  protected SpringApplicationBuilder configure(
                                  SpringApplicationBuilder builder) {
    return builder.sources(IngredientServiceApplication.class);
  }
  
}
