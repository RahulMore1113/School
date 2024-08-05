package com.rahul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rahul.model.Profile;
import com.rahul.service.IProfileService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProfileController {

	@Autowired
	private IProfileService service;

	@GetMapping("/displayProfile")
	public ModelAndView displayProfile(HttpSession session) {

		Profile profile = service.getProfile(session);

		if (profile == null)
			return new ModelAndView("error.html").addObject("message", "User not logged in");

		return new ModelAndView("profile.html").addObject("profile", profile);

	}

	@PostMapping("/updateProfile")
	public String updateProfile(@Valid @ModelAttribute Profile profile, Errors errors, HttpSession session,
			RedirectAttributes redirectAttributes) {

		if (errors.hasErrors())
			return "profile.html";

		try {
			service.updateProfile(profile, session);
			redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
		} catch (Exception e) {
			log.error("Error updating profile", e);
			redirectAttributes.addFlashAttribute("error", "Error updating profile");
		}

		return "redirect:/displayProfile";

	}

}
