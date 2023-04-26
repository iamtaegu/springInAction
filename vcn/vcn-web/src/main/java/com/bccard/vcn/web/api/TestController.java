package com.bccard.vcn.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path="/vcn") //, produces = "application/json")
@CrossOrigin(origins = "*")//CORS 허용
public class TestController {
//
//    @Autowired
//    private VisaApiClient visApiClient;

    @GetMapping
    public String getVcn() {
        return "testVcn";
    }

    @ResponseBody
    @GetMapping("requestBody")
    public String requestBodyToJsonString(@RequestBody String messageBody) {
        return messageBody;
    }

    @GetMapping("get_flux")
    public Flux<String> getFlux() {
        return Flux.just("getFLux");
    }
/*
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
*/
}
