package com.rahul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@SpringBootApplication
public class SchoolAdminServerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolAdminServerAppApplication.class, args);
	}

}
