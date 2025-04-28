package com.bway.springdemo.service;

import com.bway.springdemo.model.Contact;

public interface EmailService {
	public void sendContactEmail(Contact contact);
    public void sendEmail(String to, String subject, String text);

}
