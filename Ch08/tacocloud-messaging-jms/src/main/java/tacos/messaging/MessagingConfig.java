package tacos.messaging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import tacos.Order;

@Configuration
public class MessagingConfig {

  /**
   * 메시지 변환기 구현하기
   * MappingJackson2MessageConverter: Jackson 2 JSON 라이브러리를 사용하여 객체를 JSON 형태로 변환
   * setTypeIdPropertyName, setTypeIdMappings 설정으로 key - order 매핑처리
   */
  @Bean
  public MappingJackson2MessageConverter messageConverter() {
    MappingJackson2MessageConverter messageConverter =
                            new MappingJackson2MessageConverter();
    messageConverter.setTypeIdPropertyName("_typeId");
    
    Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
    typeIdMappings.put("order", Order.class);
    messageConverter.setTypeIdMappings(typeIdMappings);
    
    return messageConverter;
  }

}
