package com.rahul.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class ProjectConfiguration {

	@Bean
	BasicAuthRequestInterceptor basicAuthRequestInterceptor() {

        return new BasicAuthRequestInterceptor("admin@gmail.com", "admin");

	}

	@Bean
	RestTemplate restTemplate() {

        return new RestTemplateBuilder()
                .basicAuthentication("admin@gmail.com", "admin")
                .build();

	}

	@Bean
	WebClient webClient() {

        return WebClient
                .builder()
                .filter(ExchangeFilterFunctions
                        .basicAuthentication("admin@gmail.com", "admin"))
                .build();

	}

}
