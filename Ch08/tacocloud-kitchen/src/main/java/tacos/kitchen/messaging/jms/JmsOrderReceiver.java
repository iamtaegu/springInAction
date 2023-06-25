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

  /**
   * 도착지로부터 메시지를 가져오는데,
   * 메시지 변환은 내부적으로 처리되고,
   * 주문 객체로 변환
   */
  @Override
  public Order receiveOrder() {
    return (Order) jms.receiveAndConvert("tacocloud.order.queue");
  }
  
}
