package tacos.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Order;
import tacos.Taco;
import tacos.data.TacoRepository;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/design", produces = "application/json") // application/json인 요청만 처리
/**
 * 서로 다른 도메인 간의 요청을 허용
 * 서로 다른 도메인 간 통신은 브라우저가 막는데,
 * 서버 응답에 CORS 헤더를 포함시켜 극복할 수 있고, 아래 애노테이션이 동일하게 처리
 */
@CrossOrigin(origins = "*")
public class ApiDesignTacoController {
    private TacoRepository tacoRepo;
    //@Autowired
    //EntityLinks entityLinks;

    public ApiDesignTacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());

        List<Taco> tacos = tacoRepo.findAll(page).getContent();

        return tacos;

    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        if (optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.NOT_FOUND);
        }
        // return null; HTTP 상태코드는 200으로 반환하기 때문에 아래 404로 개선
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Content-type이 application/json인 경우만 처리
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

}
