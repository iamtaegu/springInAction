package tacos.kitchen.messaging.jms.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import tacos.Order;
import tacos.kitchen.KitchenUI;

@Profile("jms-listener")
@Component
public class OrderListener {
  
  private KitchenUI ui;

  @Autowired
  public OrderListener(KitchenUI ui) {
    this.ui = ui;
  }

  /**
   * 도착지의 메시지를 수신하기 위해
   * JMS 메시지에 반응하는 메시지 리스너 생성을 위한 @JmsListener 명시
   *
   * JmsTemplate를 사용하지 않으며, 애플리케이션 코드에서 호출하지 않음
   * 스프링 프레임워크에서 지정 도착지에서 메시지가 도착하면
   * Order 객체를 인자로 전달하면서 receiveOrder 메소드를 호출
   */
  @JmsListener(destination = "tacocloud.order.queue")
  public void receiveOrder(Order order) {
    ui.displayOrder(order);
  }
  
}
