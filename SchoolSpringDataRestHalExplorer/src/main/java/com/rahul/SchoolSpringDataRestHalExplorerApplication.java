package com.rahul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class SchoolSpringDataRestHalExplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolSpringDataRestHalExplorerApplication.class, args);
	}

}
