package tacos.web.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//end::recents[]
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//tag::recents[]
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.Taco;
import tacos.data.TacoRepository;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {
  private TacoRepository tacoRepo;

  public DesignTacoController(TacoRepository tacoRepo) {
    this.tacoRepo = tacoRepo;
  }

  /* 스프링 MVC
  publc Iterable<Taco> mvcRecentTacos() {
    PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
    return tacoRepo.findAll(page).getContent();
  }
  */

  /* RxJava 타입으로 처리
  public Observable<Taco> recentTacos(){
    return tacoService.getRecentTacos();
  }

   */

  @GetMapping("/recent")
  public Flux<Taco> recentTacos() {
    /**
     * 컨트롤러에서 리액터 타입으로 변환할 필요가 없게
     * 리퍼지터리에서 리액터(Flux)를 반환하도록 작성
     * 리퍼지터리로부터 리액티브 타입을 받을 때 subscribe()를 호출할 필요가 없는건
     * 프레임워크가 호출해 주기 때문
     */
    return tacoRepo.findAll().take(12);
  }

  /**
   * 리액티브하게 입력 처리하기
   * 스프링 MVCN 입력 처리의 딜레이 포인트 - 두 번의 블로킹이 있음
   * [1] Http body의 콘텐츠와 결합된 Taco 객체를 입력으로 받는데,
   * 이것은 요청 페이로드(Http header가 아닌 Http body)가 완전하게 분석되어 Taco 객체를 생성하는데 사용될 수 있어야 postTaco()가 호출될 수 있다는 걸 의미
   * [2] 리퍼지토리의 save() 메서드의 블로킹되는 호출이 끝나고 복귀돼야 postTaco() 응답 처리 될 수 있음
   *
   */
  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Taco> postTaco(@RequestBody Mono<Taco> tacoMono) {
    return tacoRepo.saveAll(tacoMono).next();
  }

  /**
   * 리액티브 타입 객체를 반환하는 것을
   * 스프링 WebFlux가 리액티브 방식으로 응답을 처리
   */
  @GetMapping("/{id}")
  public Mono<Taco> tacoById(@PathVariable("id") String id) {
    return tacoRepo.findById(id);
  }

}
