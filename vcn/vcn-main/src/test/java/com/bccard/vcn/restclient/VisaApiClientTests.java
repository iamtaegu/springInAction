package com.bccard.vcn.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class VisaApiClientTests {

    @Autowired
    private VisaApiClient visaApiClient;
    @Autowired
    private ObjectMapper om;

    @Test
    public void visaApi_requisitionService() throws JsonProcessingException {
        String returnStr = visaApiClient.getVcnV1(new HashMap<>());

        System.out.println(returnStr);

        Map<String, Object> returnMap = om.readValue(returnStr, Map.class);
        try {

            returnMap.forEach((k, v) -> System.out.println(k + ":" + v));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
