package com.bccard.vcn.web.api;

import com.bccard.vcn.restclient.VisaApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @PostMapping
    public Map<String, String> getVcnV1(HttpServletRequest request) {
        String[] paramNames = request.getParameterValues("action");
        String[] paramValues = request.getParameterValues("action_val");
        Map<String, String> paramMap = new HashMap<>();

        for (int i=0;i<paramNames.length;i++) {
            System.out.println("[TestController] " + paramNames[i] + ":" + paramValues[i]);
            paramMap.put(paramNames[i], paramValues[i]);
        }

        Map<String, String> respMap = visApiClient.getVcnV1(paramMap);

        System.out.println("[TestController] paramValue: " + respMap.get("status"));
        return respMap;
    }

}
