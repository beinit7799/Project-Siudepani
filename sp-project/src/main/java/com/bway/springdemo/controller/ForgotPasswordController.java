package com.bway.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bway.springdemo.model.User;
import com.bway.springdemo.service.EmailService;
import com.bway.springdemo.service.UserService;

import java.util.Random;

@Controller
public class ForgotPasswordController {
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService; // Your service to get user by email

    private String recoveryCode;
    private String userEmail;

    @GetMapping("/forgotPassword")
    public String forgotPasswordForm() {
        return "users/ForgotPassword"; // HTML form to input email
    }

    @PostMapping("/send-recovery-code")
    public String sendRecoveryCode(@RequestParam("email") String email, Model model) {
        User user = userService.getUserByEmail(email);

        if (user == null) {
            model.addAttribute("error", "No user found with this email.");
            return "users/ForgotPassword";
        }

        recoveryCode = String.format("%06d", new Random().nextInt(999999));
        userEmail = email;

        emailService.sendEmail(email, "Your Password Recovery Code", "Recovery Code: " + recoveryCode);

        return "users/Verify_code"; // HTML form to input the code
    }

    @PostMapping("/verify-code")
    public String verifyRecoveryCode(@RequestParam("code") String code, Model model) {
        if (code.equals(recoveryCode)) {
            return "users/Reset_password"; // HTML form to set new password
        } else {
            model.addAttribute("error", "Invalid recovery code.");
            return "users/Verify_code";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        if (newPassword.equals(confirmPassword)) {
            User user = userService.getUserByEmail(userEmail);
            user.setPassword(passwordEncoder.encode(newPassword));

            userService.UpdateUser(user); 
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Passwords do not match.");
            return "users/Reset_password"; 
        }
    }

}

