package com.bccard.vcn.web.api;

import com.bccard.vcn.domain.tacos.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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


}
