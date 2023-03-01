package tacos.data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tacos.Taco;

/**
 * 사용자가 생성한 타코 디자인
 */
//public interface TacoRepository {
public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {
	//Taco save(Taco design);
}