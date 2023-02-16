package tacos;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class Order {
	
	@NotBlank(message="Name is required")
	private String deliveryName;
	
	@NotBlank(message="Street is required")
	private String deliveryStreet;
	
	@NotBlank(message="City is required")
	private String deliveryCity;
	
	@NotBlank(message="State is required")
	private String deliveryState;
	
	@NotBlank(message="Zip code is required")
	private String deliveryZip;

	/**
	 * 값이 있는지와 입력 값이 유효한 신용 카드 번호인지 확인 필요
	 * @CreditCardNumber
	 * 	> Luhn 알고리즘 검사에 합격한 유효한 신용 카드 번호여야 한다는 것을 선언
	 * 	> 단, 금융망과 연동된 것은 아니기 때문에 실제 신용 카드 유효성 검증은 없음
	 */
	@CreditCardNumber(message="Not a valid credit card number")
	private String ccNumber;

	/**
	 * MM/YY 형식의 검사를 지원하는 애노테이션이 없기 때문에
	 * 정규식 패턴으로 검증
	 */
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
			message="Must be formatted MM/YY")
	private String ccExpiration;

	/**
	 * 입력한 값이 정확하게 세 자리 숫자인지 검사
	 */
	@Digits(integer=3, fraction=0, message="Invalid CVV")
	private String ccCVV;
}