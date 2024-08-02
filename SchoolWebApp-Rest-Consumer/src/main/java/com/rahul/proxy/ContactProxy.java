package com.rahul.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rahul.config.ProjectConfiguration;
import com.rahul.model.Contact;

import feign.Headers;

@FeignClient(name = "contact", url = "http://localhost:8080/api/contact",
        configuration = ProjectConfiguration.class)
public interface ContactProxy {

	@GetMapping("/getMessagesByStatus")
	@Headers(value = "Content-Type: application/json")
	List<Contact> getMessagesByStatus(@RequestParam String status);

}
