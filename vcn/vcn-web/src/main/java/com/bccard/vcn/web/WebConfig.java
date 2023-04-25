package com.bccard.vcn.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/test").setViewName("index");
    }

    /**
     * 기본 URI를 갖느 WebClient 빈 생성
     */
    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:8081");
    }

}
