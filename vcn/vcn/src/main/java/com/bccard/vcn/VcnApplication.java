package com.bccard.vcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(scanBasePackages = "com.bccard.vcn")
public class VcnApplication {

	public static void main(String[] args) {
		SpringApplication.run(VcnApplication.class, args);
	}
}
