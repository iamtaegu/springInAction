/**
 * 컨터버 클래스 변경 이력
 * [2023-02-20 ch03_JPA] JDBC > JPA 전환에 따른 변경
 */
package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.util.Optional;

/**
 * 데이터의 타입을 변환해 주는 컨버터
 * 스프링의 Converter 인터페이스에 정의된 convert() 메서드를 구현
 * 따라서 우리가 Converter에 지정한 타입 변환이 필요할 때 convert() 메서드가 자동 호출
 *  > String 타입의 식자재 ID를 사용해서
 *  > 데이터베이스에 저장된 특성 식자재 데이터를 읽은 후
 *  > Ingredient 객체로 변환하기 위해 컨버터 사용
 * @Component 지정으로 스프링 컨테이너 빈 등록
 * @Autowired 생성자 지정으로 IngredientRepository 주입 받음
 */
@Component
public class IngredientByIdConverter
		implements Converter<String, Ingredient> {
	private IngredientRepository ingredientRepo;
	
	@Autowired
	public IngredientByIdConverter(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}

	/**
	 * 스프링데이터JPA 사용을 위한 컨버터 등록은 아니고 업무 요건에 필요하여 컨버터 작성
	 * JDBC 기반에서는 IngredientRepository를 구현하는 JdbcIngredientRepository의 findById() 메서드가 실행
	 * 그러나, 스프링데이터JPA에서는 자동으로 구현된 findById() 메서드가 실행
	 * @param id
	 * @return
	 */
	@Override
	public Ingredient convert(String id) {
		//return ingredientRepo.findById(id);
		Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
		return optionalIngredient.isPresent() ? optionalIngredient.get() : null;
	}
}