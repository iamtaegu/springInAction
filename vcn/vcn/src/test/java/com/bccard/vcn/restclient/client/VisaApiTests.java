package com.bccard.vcn.restclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class VisaApiTests {

//    @Autowired
//    private VisaApiClient visaApiClient;
    @Autowired
    private ObjectMapper om;

    @Test
    public void visaApi_requisitionService() throws JsonProcessingException {
//        String resp = visaApiClient.getVcnV1(new HashMap<>());
//
//        Map<String, Object> respMap = om.readValue(resp, Map.class);
//
//        try{
//            respMap.forEach((k, v) -> System.out.println(k + ":" + v));
//        }catch(Exception e){
//            e.getMessage();
//        }

    }

}
