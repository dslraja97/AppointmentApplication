package com.example.AppointmentBook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	public boolean sendMail(String to, String subject, String message) {

		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true);
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(message, true);

			mailSender.send(msg);
			System.out.println("Email sent successfully to " + to);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("Error while creating or sending the email.");
		} catch (MailException e) {
			e.printStackTrace();
			System.out.println("Error in sending email: " + e.getMessage());
		}
		return false;

	}

}
