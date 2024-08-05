package com.rahul.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(@PathVariable int pageNum, String sortField, String sortDir) {

        Page<Contact> msgPage = service.findMsgsWithOpenStatus(pageNum, sortField, sortDir);

        Map<String, Object> attributes = Map.of(
                "currentPage", pageNum,
                "totalPages", msgPage.getTotalPages(),
                "totalMsgs", msgPage.getTotalElements(),
                "sortField", sortField,
                "sortDir", sortDir,
                "reverseSortDir", sortDir.equals("asc") ? "desc" : "asc",
                "contactMsgs", msgPage.getContent()
        );

        return new ModelAndView("messages.html", attributes);
    }

    @GetMapping("/closeMsg")
    public String closeMsg(@RequestParam int id) {

        service.updateMsgStatus(id);

        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";

    }

}
