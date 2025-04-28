package com.bway.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    private EmailService emailService;

    @Autowired
    private UserService userService; // Your service to get user by email

    private String recoveryCode;
    private String userEmail;

    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "forgotPasswordForm"; // HTML form to input email
    }

    @PostMapping("/send-recovery-code")
    public String sendRecoveryCode(@RequestParam("email") String email, Model model) {
        User user = userService.getUserByEmail(email);

        if (user == null) {
            model.addAttribute("error", "No user found with this email.");
            return "forgotPasswordForm";
        }

        recoveryCode = String.format("%06d", new Random().nextInt(999999));
        userEmail = email;

        emailService.sendEmail(email, "Your Password Recovery Code", "Recovery Code: " + recoveryCode);

        return "enterRecoveryCode"; // HTML form to input the code
    }

    @PostMapping("/verify-recovery-code")
    public String verifyRecoveryCode(@RequestParam("code") String code, Model model) {
        if (code.equals(recoveryCode)) {
            return "resetPasswordForm"; // HTML form to set new password
        } else {
            model.addAttribute("error", "Invalid recovery code.");
            return "enterRecoveryCode";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("password") String password, Model model) {
        User user = userService.getUserByEmail(userEmail);
        user.setPassword(password); // You should encrypt this if you use encryption
        userService.UpdateUser(user);

        return "redirect:/login"; // Back to login after successful reset
    }
}

