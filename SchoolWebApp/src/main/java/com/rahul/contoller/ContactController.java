package com.rahul.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rahul.model.Contact;
import com.rahul.service.IContactService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ContactController {

	@Autowired
	private IContactService service;

	@GetMapping("/contact")
	public String displayContactPage() {
		return "contact.html";
	}

	@PostMapping("/saveMsg")
	public ModelAndView saveMessage(Contact contact) {

		service.saveMessage(contact);

		return new ModelAndView("redirect:/contact");

	}

}
