package com.rahul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rahul.model.Person;
import com.rahul.repo.PersonRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

	@Autowired
	private PersonRepo repo;

	@RequestMapping("/dashboard")
	public String displayDashboard(Model model, Authentication authentication, HttpSession session) {

        Person person = repo.readByEmail(authentication.getName());

        model
                .addAttribute("username", person.getName())
                .addAttribute("roles", authentication.getAuthorities().toString());

        if (person.getEazyClass() != null && person.getEazyClass().getName() != null)
            model.addAttribute("enrolledClass", person.getEazyClass().getName());

        session.setAttribute("loggedInPerson", person);

//        throw new RuntimeException("It's been a bad day!!");

		return "dashboard.html";

	}

}
