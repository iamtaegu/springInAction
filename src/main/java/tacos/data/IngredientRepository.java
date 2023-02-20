package tacos.data;
import org.springframework.data.repository.CrudRepository;
import tacos.Ingredient;

/**
 * 식자재 정보를 저장
 */
//public interface IngredientRepository {
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
	/*
	Iterable<Ingredient> findAll();
	Ingredient findById(String id);
	Ingredient save(Ingredient ingredient);
	*/
}