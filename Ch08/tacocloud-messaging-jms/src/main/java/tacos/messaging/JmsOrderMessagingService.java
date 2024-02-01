package tacos.messaging;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import tacos.Order;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

  private JmsTemplate jms;

  @Autowired
  public JmsOrderMessagingService(JmsTemplate jms) {
    this.jms = jms;
  }

  /**
   * 1번 param, 목적지로
   * 2번 param, 객체 전달
   *  ㅇ Order 객체는 Message 객체로 변환되어 전송
   */
  @Override
  public void sendOrder(Order order) {
    jms.convertAndSend("tacocloud.order.queue", order,
        this::addOrderSource); //메서드 참조를 사용하면 람다 사용을 통한 불피룡한 중복을 막을 수 있음
  }
/*
  @Override
  public void sendOrder(Order order) {
    // 기본 목적지를 사용하는 send 메소드 호출 (Message 파라미터 1개)
    jms.send(session -> session.createObjectMessage(order));
  }

 */

  /**
   * JmsTemplate.convertAndSend 를 사용하면 Message 객체가 내부적으로 생성되기 때문에
   * 내부적으로 생성된 Message가 전송되기 전에 마지막 인자 값을 실행해줌
   *
   */
  private Message addOrderSource(Message message) throws JMSException {
    message.setStringProperty("X_ORDER_SOURCE", "WEB"); //온라인:WEB, 오프라인:STORE
    return message;
  }

}
