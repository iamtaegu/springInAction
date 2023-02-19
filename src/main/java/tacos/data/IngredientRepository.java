package tacos.data;
import tacos.Ingredient;

/**
 * 식자재 정보를 저장
 */
public interface IngredientRepository {
	Iterable<Ingredient> findAll();
	Ingredient findById(String id);
	Ingredient save(Ingredient ingredient);
}