package com.bway.springdemo.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bway.springdemo.model.Contact;
import com.bway.springdemo.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceimpl implements EmailService {
	@Autowired
    private JavaMailSender mailSender;

    @Value("${app.mail.to}")
    private String toEmail;

    @Override
    public void sendContactEmail(Contact contact) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(toEmail);// Your configured Gmail
            helper.setTo(toEmail);// Send to yourself
            helper.setSubject("Contact from: " + contact.getFullname());

            String body = "Name: " + contact.getFullname() + "\n"
                        + "Email: " + contact.getEmail() + "\n\n"
                        + "Message:\n" + contact.getMessage();

            helper.setText(body);
            helper.setReplyTo(contact.getEmail());// User's email to reply

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send contact email", e);
        }
    }
}
