package com.rahul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rahul.model.Person;
import com.rahul.service.IPersonService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private IPersonService service;

	@GetMapping("/register")
	public String displayRegisterPage(Model model) {

		model.addAttribute("person", new Person());

		return "register.html";

	}

	@PostMapping("/createUser")
	public String createUser(@Valid @ModelAttribute Person person, Errors errors) {

		if (errors.hasErrors())
			return "register.html";

		boolean isSaved = service.createNewPerson(person);

		if (isSaved)
			return "redirect:/login?register=true";
		else
			return "register.html";

	}

}
