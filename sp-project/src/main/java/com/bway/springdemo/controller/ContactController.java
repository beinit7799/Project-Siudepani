package com.bway.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bway.springdemo.model.Contact;
import com.bway.springdemo.service.EmailService;

@Controller
public class ContactController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/contact")
    public String contactForm() {
        return "contact";
    }

    @PostMapping("/submitContact")
    public String submitContact(@ModelAttribute Contact contact, Model model) {
        emailService.sendContactEmail(contact);
        model.addAttribute("msg", "Your message has been sent successfully!");
        return "redirect:users/contact";
    }

}

