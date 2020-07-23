package com.michel.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LabPlanClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabPlanClientApplication.class, args);
	}

}
