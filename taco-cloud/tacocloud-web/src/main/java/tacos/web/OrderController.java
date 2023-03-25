package tacos.web;
import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
  
  private OrderRepository orderRepo;

  /**
   * 홀더 클래스 사용을 통한 @ConfiguraitonProperties 불필요한 애노테이션 제거
   */
  private OrderProps props;

  public OrderController(OrderRepository orderRepo, 
          OrderProps props) {
    this.orderRepo = orderRepo;
    this.props = props;
  }
  
  @GetMapping("/current")
  public String orderForm(@AuthenticationPrincipal User user, 
      @ModelAttribute Order order) {
    if (order.getDeliveryName() == null) {
      order.setDeliveryName(user.getFullname());
    }
    if (order.getDeliveryStreet() == null) {
      order.setDeliveryStreet(user.getStreet());
    }
    if (order.getDeliveryCity() == null) {
      order.setDeliveryCity(user.getCity());
    }
    if (order.getDeliveryState() == null) {
      order.setDeliveryState(user.getState());
    }
    if (order.getDeliveryZip() == null) {
      order.setDeliveryZip(user.getZip());
    }
    
    return "orderForm";
  }

  /**
   * 검사를 수행한다는 것을 지정
   *  > @Valid
   * @param order
   * @param errors
   * @param sessionStatus
   * @param user
   * @return
   */
  @PostMapping
  public String processOrder(@Valid Order order, Errors errors, 
      SessionStatus sessionStatus, 
      @AuthenticationPrincipal User user) {
    
    if (errors.hasErrors()) {
      return "orderForm";
    }
    
    order.setUser(user);
    
    orderRepo.save(order);
    sessionStatus.setComplete();
    
    return "redirect:/";
  }
  
  @GetMapping
  public String ordersForUser(
      @AuthenticationPrincipal User user, Model model) {

    Pageable pageable = PageRequest.of(0, props.getPageSize());
    model.addAttribute("orders", 
        orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
    
    return "orderList";
  }

}
