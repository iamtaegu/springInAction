package com.bccard.vcn.web.api;

import com.bccard.vcn.domain.tacos.Ingredient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path="/testWebClient")
public class TestWebClient {

    @Autowired
    WebClient webClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * GET
     */
    @ResponseBody
    @GetMapping
    public Flux<Ingredient> testGet() {
        Flux<Ingredient> ingredient = webClient.get()
                .uri("/ingredients")
                .retrieve()
                //.onStatus(HttpStatus::is5xxServerError,
                .bodyToFlux(Ingredient.class);

        ingredient.timeout(Duration.ofSeconds(1))
                        .subscribe(i -> System.out.println(i),
                                   e -> {
                                        // handle timeout error
                                        System.out.println(e);
                                   }

                        );
        return ingredient;
    }

    /**
     * POST
     */
    @ResponseBody
    @PostMapping
    public Mono<Ingredient> testPost(@RequestBody Mono<Ingredient> params) {

        params.subscribe(i -> System.out.println(i));

        /* Mono<Ingredient> ingredientMono = Mono.just(new Ingredient("100", "100", Ingredient.Type.CHEESE));

        Mono<Ingredient> returnMono = webClient.post()
                .uri("/ingredients")
                .body(ingredientMono, Ingredient.class)
                .retrieve()
                .bodyToMono(Ingredient.class);

        returnMono.subscribe(i -> System.out.println(i)); */

        //return returnMono;

        return Mono.just(new Ingredient("100", "100", Ingredient.Type.SAUCE));
    }

}
