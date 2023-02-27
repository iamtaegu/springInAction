package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;
import javax.validation.Valid;
import org.springframework.validation.Errors;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
/*
	private int pageSize = 5;

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}*/
	//구성 홀더 정의하기
	private OrderProps props;

	private OrderRepository orderRepo;
	
	public OrderController(OrderRepository orderRepo, OrderProps props) {
		this.orderRepo = orderRepo;
		this.props = props;
	}

	/**
	 * 보안 켄텍스트로부터 인증된 사용자 정보를 가져와
	 * 주문 폼에 미리 보여줌
	 * @return
	 */
	@GetMapping("/current")
	//public String orderForm(Model model) {
	public String orderForm(@ModelAttribute Order order) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

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

		//클래스 수준 @SessionAttribute()로 대체
		//model.addAttribute("order", new Order());
		return "orderForm";
	}

	/**
	 * Order 객체에 User 엔티티 속성이 추가됨에 따라 
	 * 주문시 User 값을 설정해줌
	 * 단, User가 누구인지 결정하는 방법은 여러가지가 있고 @AuthenticationPrincipal 애노테이션 사용이 무난
	 * @param order
	 * @param errors
	 * @param sessionStatus
	 * @param user
	 * @return
	 */
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus
	, @AuthenticationPrincipal User user) {
		if (errors.hasErrors()) {
			return "orderForm";
		}

		// 주문에 사용자 매핑
		order.setUser(user);

		/**
		 * Order 객체가 저장된 다음에는 세션에 유지될 필요 없고, 유지되면 이슈가 발생할 수 있음
		 * sessionStatus.setComplete() 호출로 세션을 재설정
		 */
		orderRepo.save(order);
		sessionStatus.setComplete();
		
		return "redirect:/";
	}

	@GetMapping
	public String ordersForUser(
			@AuthenticationPrincipal User user, Model model
	) {

		Pageable pageable = PageRequest.of(0, 20);
		model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));

		return "orderList";
	}
}