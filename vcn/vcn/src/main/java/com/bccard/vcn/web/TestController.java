package com.bccard.vcn.web;

import com.bccard.vcn.client.restclient.VisaApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path="/mainvcn") //, produces = "application/json")
@CrossOrigin(origins = "*")//CORS 허용
public class TestController {

    @Autowired
    private VisaApiClient visApiClient;

    @GetMapping
    public String getVcn() {
        return "getVcn";
    }

    @PostMapping
    public String getVcnV1(HttpServletRequest request) throws JsonProcessingException {
        String[] paramNames = request.getParameterValues("action");
        String[] paramValues = request.getParameterValues("action_val");
        Map<String, String> paramMap = new HashMap<>();

        for (int i=0;i<paramNames.length;i++) {
            if (paramNames[i] != null ) {
                System.out.println("[TestController] " + paramNames[i] + ":" + paramValues[i]);
                paramMap.put(paramNames[i], paramValues[i]);
            }
        }

        return visApiClient.getVcnV1(paramMap);
    }

    /* @GetMapping("get_webClinet")
    public String test_get_webClinet() {
        System.out.println(webClient.get().retrieve());

        //오래 실행되는 요청 타임아웃시키기
        Flux<Object> ingredients = webClient
                .get()
                .uri("http://localhost:8080/ingredients")
                .retrieve()
                .bodyToFlux(Object.class);
        ingredients.timeout(Duration.ofSeconds(1))
                .subscribe(i -> System.out.println(i),
                        error -> {
                            //handle timeout error
                        });


        return "ok";
    } */

   /* @GetMapping("get_ingredient")
    public String test_get_ingredient() {

        //WebClient 인스턴스 생성
        Mono<Object> ingredients = (Mono<Object>) WebClient.create()
                .get().uri("http://localhost:8080/ingredients")
                .retrieve() // 요청 실행
                .onStatus(HttpStatus::is4xxClientError,
                        response -> Mono.just(new Exception()))
                //첫 번째 인자는 HttpStatus를 지정하는 조건식, 우리가 처리를 원하는 상태코드라면 true
                //true인 경우 두 번쨰 인자의 함수로 응답이 반환되고, 함수 실행
                .bodyToMono(Object.class); // 응답 몸체의 페이로드를 Mono<Ingredient>로 추출
        ingredients.subscribe(i -> System.out.println(i.toString())
                ,error -> {
                    System.out.println(error.toString());
                } ); //
        return "ok";
    } */

}
