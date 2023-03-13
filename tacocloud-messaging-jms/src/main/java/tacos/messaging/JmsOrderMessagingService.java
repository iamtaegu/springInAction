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
   * jms.convertAndSend
   * param1는 도착지, 도착지를 생략하면 application.yml에 있는 default 도착지
   * parma2는
   * @param order
   */
  @Override
  public void sendOrder(Order order) {
    jms.convertAndSend("tacocloud.order.queue", order,
        this::addOrderSource);
  }
  
  private Message addOrderSource(Message message) throws JMSException {
    message.setStringProperty("X_ORDER_SOURCE", "WEB");
    return message;
  }

}
