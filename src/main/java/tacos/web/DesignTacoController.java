package tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import tacos.Taco;
import tacos.Order;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import javax.validation.Valid;
import org.springframework.validation.Errors;
import tacos.data.TacoRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@RequestMapping("/design")
/**
 * 주문 하나에 다수의 타코가 있을 수 있고, 다수의 HTTP 요청이 주문에 필요한 타코(객체)를 위해 발생될 수 있음
 * 클래스 수준의 @SessionAttributes("order")를 주문과 같은 모델 객체에 지정하면
 * 세션에서 계속 보존되면서 다수의 요청을 처리
 */
@SessionAttributes("order")
public class DesignTacoController {
	private final IngredientRepository ingredientRepo;
	
	private TacoRepository tacoRepo;

	/**
	 * 생성자 주입
	 * 생성자의 호출 시점에 1회 호출 되는 것이 보장
	 *  > 주입받은 객체가 변하지 않거나, 반드시 객체의 주입이 필요한 경우에 강제하기 위해 사용
	 * 생성자가 1개만 있을 경우에 @Autowired를 생략해도 주입이 가능
	 * @param ingredientRepo
	 * @param tacoRepo
	 */
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
		this.ingredientRepo = ingredientRepo;
		this.tacoRepo = tacoRepo;
	}

	/**
	 * 모든 식자재 데이터를 가져오고
	 * 타입별로 필터링
	 * @param model
	 * @return
	 */
	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(),
					filterByType(ingredients, type));
		}
		
		model.addAttribute("taco", new Taco());
		
		return "design";
	}
	
	private List<Ingredient> filterByType(
			List<Ingredient> ingredients, Type type) {
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}
	
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	/**
	 *
	 * @param design
	 * @param errors
	 * @param order
	 * 	> @ModelAttribute 애노테이션을 사용하여 모델로 부터 전달되어야 하고,
	 * 	> 스프릥MVC가 매개변수를 바인딩하지 않아야 함을 명시
	 * @return
	 */
	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
		//1) 전달된 데이터의 유효성 검사
		if (errors.hasErrors()) {
			return "design";
		}

		//2) 타코 저장
		Taco saved = tacoRepo.save(design);
		//3) 세션에 보존된 Order에 Taco 객체 추가
		order.addDesign(saved);
		
		return "redirect:/orders/current";
	}
}