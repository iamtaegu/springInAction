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

    //@Autowired
    //private VisaApiClient visaApiClient;
    @Autowired
    private ObjectMapper om;

    @Test
    public void visaApi_requisitionService() throws JsonProcessingException {

    }
}
