package tacos.tacos;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

/**
 * infoContributor 커스텀 구현 클래스
 */
@Component
public class TacoCountInfoContributor implements InfoContributor {

  private TacoRepository tacoRepo;

  public TacoCountInfoContributor(TacoRepository tacoRepo) {
    this.tacoRepo = tacoRepo;
  }

  /**
   * InfoContributor.contribute()
   * 타코 개수 정보를 /info 엔드포인트에 추가함
   * */
  @Override
  public void contribute(Builder builder) {
    long tacoCount = tacoRepo.count();
    Map<String, Object> tacoMap = new HashMap<String, Object>();
    tacoMap.put("count", tacoCount);
    builder.withDetail("taco-stats", tacoMap);
  }

}
