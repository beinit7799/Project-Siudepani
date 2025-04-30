package com.bway.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bway.springdemo.model.User;
import com.bway.springdemo.service.EventImageService;
import com.bway.springdemo.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private EventImageService eventImageService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute User user, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.userSignup(user);
        return "redirect:/users/login";
    }

    @GetMapping("/userView")
    public String getUserView(Model model) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username of the logged-in user

        // Retrieve the user from the database using the username
        User user = userService.getUserByUsername(username);

        // Add the user to the model so you can display it in the view
        model.addAttribute("user", user);

        return "users/userView"; // Return the view to display the user details
    }

    @GetMapping("/home")
    public String home(HttpServletResponse response,Model model) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        model.addAttribute("broadcastMessage", AdminController.getBroadcastMessage());
        return "users/home";
    }

    @GetMapping("/contact")
    public String getContact() {
        return "users/contact";
    }
    
    @GetMapping("/gallery")
    public String getGallery(Model model) {
        model.addAttribute("images", eventImageService.getAllImages());
        return "users/Gallery";
    }
    
    
}
