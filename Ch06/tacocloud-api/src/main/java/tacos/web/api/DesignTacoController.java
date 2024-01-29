//tag::recents[]
package tacos.web.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import tacos.Taco;
import tacos.data.TacoRepository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
// 응답을 application/json으로 줄거기 때문에
// Accept 헤더에 application/json이 포함된 요청만을 처리
@RequestMapping(path="/design",                      // <1>
                produces="application/json")
// 프론트의 실행 도메인(포트)가 다르기 때문에 브라우저가 실행을 막음
// 이를 위해 서버 응답에 CORS(Cross-Origin Resource Sharing) 헤더를 포함해 허용
@CrossOrigin(origins="*")        // <2>
public class DesignTacoController {
  private TacoRepository tacoRepo;
  
  @Autowired
  EntityLinks entityLinks;

  public DesignTacoController(TacoRepository tacoRepo) {
    this.tacoRepo = tacoRepo;
  }

  @GetMapping("/recent")
  public Iterable<Taco> recentTacos() {                 //<3>
    PageRequest page = PageRequest.of(
            0, 12, Sort.by("createdAt").descending());
    return tacoRepo.findAll(page).getContent();
  }
  //end::recents[]

  @GetMapping("/recenth")
  public Resources<TacoResource> recentTacosH() {
    PageRequest page = PageRequest.of(
            0, 12, Sort.by("createdAt").descending());
    List<Taco> tacos = tacoRepo.findAll(page).getContent();

    // 6.2.2 리소스 어셈블러
    // ResourceAssemblerSupport.toResources
    List<TacoResource> tacoResources =
        new TacoResourceAssembler().toResources(tacos);
    Resources<TacoResource> recentResources =
        new Resources<TacoResource>(tacoResources);
    // 6.2.1 하이퍼링크 추가
    recentResources.add(
        linkTo(methodOn(DesignTacoController.class).recentTacos())
        .withRel("recents"));
    return recentResources;
  }

//ControllerLinkBuilder.linkTo(DesignTacoController.class)
//.slash("recent")
//.withRel("recents"));

  
  
  
//  @GetMapping("/recenth")
//  public Resources<TacoResource> recenthTacos() {
//    PageRequest page = PageRequest.of(
//            0, 12, Sort.by("createdAt").descending());
//    List<Taco> tacos = tacoRepo.findAll(page).getContent();
//
//    List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);
//    
//    Resources<TacoResource> tacosResources = new Resources<>(tacoResources);
////    Link recentsLink = ControllerLinkBuilder
////        .linkTo(DesignTacoController.class)
////        .slash("recent")
////        .withRel("recents");
//
//    Link recentsLink = 
//        linkTo(methodOn(DesignTacoController.class).recentTacos())
//        .withRel("recents");
//    tacosResources.add(recentsLink);
//    return tacosResources;
//  }

  // POST: Client에서 Server로 데이터를 전송
  // Content-type이 application/json과 일치하는 요청만 처리
  //tag::postTaco[]
  @PostMapping(consumes="application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public Taco postTaco(@RequestBody Taco taco) {
    return tacoRepo.save(taco);
  }
  //end::postTaco[]

//  @GetMapping("/{id}")
//  public Taco tacoById(@PathVariable("id") Long id) {
//    Optional<Taco> optTaco = tacoRepo.findById(id);
//    if (optTaco.isPresent()) {
//      return optTaco.get();
//    }
//    return null;
//  }

  /**
   * 플레이스홀더 {id}
   * 콘텐츠가 없는 경우 4040 반환
   */
  @GetMapping("/{id}")
  public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
    Optional<Taco> optTaco = tacoRepo.findById(id);
    if (optTaco.isPresent()) {
      return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  
//tag::recents[]
}
//end::recents[]

