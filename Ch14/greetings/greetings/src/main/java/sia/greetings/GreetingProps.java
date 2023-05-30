package sia.greetings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "greeting")
@Component
public class GreetingProps {
  
  private String message;

  /**
   * config-server에 속성이 변경 되면
   * curl localhost:8080/actuator/refresh -X POST
   * 형태로 수동 리프레쉬 시킬 수 있음
   * @return
   */
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
}