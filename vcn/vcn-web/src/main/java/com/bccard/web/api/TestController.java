package com.bccard.web.api;

import com.bccard.vcn.client.restclient.VisaApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")//CORS 허용
@RequestMapping(path="/vcn",
        produces = "application/json")
@RestController
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

}
