package com.rahul.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rahul.model.Contact;
import com.rahul.service.IContactService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ContactController {

	@Autowired
	private IContactService service;

	@GetMapping("/contact")
	public String displayContactPage(Model model) {
		model.addAttribute("contact", new Contact());
		return "contact.html";
	}

	@PostMapping("/saveMsg")
	public String saveMessage(@Valid @ModelAttribute Contact contact, Errors errors) {

		if (errors.hasErrors()) {
			log.error("Contact form validation failed due to : " + errors.toString());
			return "contact.html";
		}

		service.saveMessageDetails(contact);

		return "redirect:/contact";

	}

	@GetMapping("/displayMessages")
	public ModelAndView displayMessages() {

		return new ModelAndView("messages.html").addObject("contactMsgs", service.findMsgsWithOpenStatus());

	}

	@GetMapping("/closeMsg")
	public String closeMsg(@RequestParam int id) {

		service.updateMsgStatus(id);

		return "redirect:/displayMessages";

	}

}
