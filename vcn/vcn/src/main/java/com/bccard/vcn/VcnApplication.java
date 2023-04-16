package com.bccard.vcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class VcnApplication {

	public static void main(String[] args) {
		SpringApplication.run(VcnApplication.class, args);
	}

	// WebClinet 기본 도메인 설정 후 Bean 등록
	@Bean
	public WebClient webClient() {
		return WebClient.create("http://localhost:8080");
	}
}
