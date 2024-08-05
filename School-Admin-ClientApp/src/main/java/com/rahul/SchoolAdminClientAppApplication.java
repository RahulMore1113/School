package com.rahul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class SchoolAdminClientAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolAdminClientAppApplication.class, args);
	}

}
