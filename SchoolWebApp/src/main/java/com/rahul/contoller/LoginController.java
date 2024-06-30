package com.rahul.contoller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping("/login")
    @PostMapping("/login")
    public String displayLoginPage(@RequestParam(required = false) String error,
                                   @RequestParam(required = false) String logout,
                                   @RequestParam(required = false) String register,
                                   Model model) {

		String errorMsg = null;

		if (error != null)
			errorMsg = "Username or Password is incorrect !!";
		else if (logout != null)
			errorMsg = "You have been successfully logged out !!";
		else if (register != null)
			errorMsg = "Your registration successful. Login with registered credentials !!";

		model.addAttribute("errorMessage", errorMsg);

		return "login.html";

	}

	@GetMapping("/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null)
			new SecurityContextLogoutHandler().logout(request, response, auth);

		return "redirect:/login?logout=true";

	}

}
