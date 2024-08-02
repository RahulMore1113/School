package com.rahul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.rahul.model.Contact;
import com.rahul.model.Response;
import com.rahul.proxy.ContactProxy;

import reactor.core.publisher.Mono;

@RestController
public class ContactController {

	@Autowired
	private ContactProxy proxy;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient webClient;

	@GetMapping("/getMessages")
	private List<Contact> getMessages(@RequestParam String status) {

		return proxy.getMessagesByStatus(status);

	}

	@PostMapping("/saveMsg")
	public ResponseEntity<Response> saveMsg(@RequestBody Contact contact) {

		String uri = "http://localhost:8080/api/contact/saveMsg";

		HttpHeaders headers = new HttpHeaders();
		headers.add("invocationFrom", "RestTemplate");

//        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(contact, headers), Response.class);
        return restTemplate.postForEntity(uri, new HttpEntity<>(contact, headers), Response.class);

	}

	@PostMapping("/saveMessage")
	public Mono<Response> saveMessage(@RequestBody Contact contact) {

		String uri = "http://localhost:8080/api/contact/saveMsg";

        return webClient
                .post()
                .uri(uri)
                .header("invocationFrom", "WebClient")
                .body(Mono.just(contact), Contact.class)
                .retrieve()
                .bodyToMono(Response.class);

	}

}
