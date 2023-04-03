package com.bccard.vcn.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class VisaApiClient {

    private RestTemplate rest;

    public VisaApiClient() {
        rest = new RestTemplate();
    }

    public Map<String, String> getVcnV1(Map<String, String> requestParam) {
        Map<String, String> respMap = new HashMap<>();
        try {
             rest.postForObject("https://sandbox.api.visa.com/vpa/v1/requisitionService",
                    requestParam, Map.class);
        } catch (HttpClientErrorException clientError) {

        }
        return
    }

}
