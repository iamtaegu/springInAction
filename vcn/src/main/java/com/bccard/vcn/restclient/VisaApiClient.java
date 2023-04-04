package com.bccard.vcn.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class VisaApiClient {

    private RestTemplate rest;
    private ObjectMapper om;

    public VisaApiClient() {
        rest = new RestTemplate();
        om = new ObjectMapper();
    }

    public String getVcnV1(Map<String, String> requestParam) throws JsonProcessingException {
        ResponseEntity<Map> respMap = null;
        String returnStr = null;

        try {
            respMap = rest.postForEntity("https://sandbox.api.visa.com/vpa/v1/requisitionService",
                    requestParam, Map.class);
        } catch (HttpClientErrorException clientError) {
            returnStr = clientError.getResponseBodyAsString();
        }

        return returnStr != null ? returnStr : om.writeValueAsString(respMap.getBody());
    }

}
