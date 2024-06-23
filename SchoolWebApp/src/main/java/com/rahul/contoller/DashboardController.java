package com.rahul.contoller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication) {

        model
                .addAttribute("username", authentication.getName())
                .addAttribute("roles", authentication.getAuthorities().toString());

        throw new RuntimeException("It's been a bad day!!");

//        return "dashboard.html";

    }

}
