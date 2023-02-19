package tacos.data;
import tacos.Taco;

/**
 * 사용자가 생성한 타코 디자인
 */
public interface TacoRepository {
	Taco save(Taco design);
}