package com.rahul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.rahul.proxy")
public class SchoolWebAppRestConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolWebAppRestConsumerApplication.class, args);
	}

}
