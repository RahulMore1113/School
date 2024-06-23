package com.rahul.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@GetMapping("/login")
    @PostMapping("/login")
    public String displayLoginPage(@RequestParam(required = false) String error,
                                   @RequestParam(required = false) String logout, Model model) {

        String errorMsg = null;

        if (error != null)
            errorMsg = "Username or Password is incorrect !!";

        if (logout != null)
            errorMsg = "You have been successfully logged out !!";

        model.addAttribute("errorMessge", errorMsg);

        return "login.html";

    }

}
