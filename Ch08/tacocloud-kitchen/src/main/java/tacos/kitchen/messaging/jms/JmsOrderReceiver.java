package tacos.kitchen.messaging.jms;

import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import tacos.Order;
import tacos.kitchen.OrderReceiver;

@Profile("jms-template")
@Component("templateOrderReceiver")
public class JmsOrderReceiver implements OrderReceiver {

  private JmsTemplate jms;

  public JmsOrderReceiver(JmsTemplate jms) {
    this.jms = jms;
  }

  /** v2.0
   * 도착지를 문자열로 지정하였고
   * 도착지로부터 메시지를 가져오는데 메시지 변환은 내부적으로 처리되어 주문 객체로 변환
   * 수신 메시지의 타입 ID 속성은 해당 메시지를 Order 객체로 변환해주고
   * 변환 객체의 타입은 Object이기 때문에 Order 객체로 캐스팅
   */
  @Override
  public Order receiveOrder() {
    return (Order) jms.receiveAndConvert("tacocloud.order.queue");
  }

  /** v1.0
   *
   */
  /*
  private MessageConverter converter;

  @Override
  public Order receiveOrder() {
    Message message = jms.receive("tacocloud.order.queue");
    return (Order) converter.fromMessage(message);
  }
   */
}
