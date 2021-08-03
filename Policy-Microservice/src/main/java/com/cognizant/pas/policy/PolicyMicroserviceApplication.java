package com.cognizant.pas.policy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PolicyMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolicyMicroserviceApplication.class, args);
	}

}
