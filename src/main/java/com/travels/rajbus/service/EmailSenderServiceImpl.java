package com.travels.rajbus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
public class EmailSenderServiceImpl {

	@Autowired
	private JavaMailSender mailSender;

	public void sendSimpleEmail(String toEmail, String subject, String text) {
//		Random r;
		try {

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(toEmail);
			mailMessage.setSubject(subject);
			mailMessage.setText(text);
			mailMessage.setFrom("vishnuchinthala5462@gmail.com");
			mailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendEmailWithAttachment(String toEmail, String body, String subject, String attachment)
			throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("vishnuchinthala5462@gmail.com");
		mimeMessageHelper.setTo(toEmail);
		mimeMessageHelper.setText(body);
		mimeMessageHelper.setSubject(subject);

		FileSystemResource fileSystem = new FileSystemResource(new File(attachment));

		mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);

		mailSender.send(mimeMessage);

	}

	private void sendemail(String email, String restpasswordLink) throws UnsupportedEncodingException, Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("vishnuchinthala5462@gmail.com", " rajbus");
		helper.setTo(email);
		String subject = "Here's the link to reset your password";
		String content = "Hello" + "you have request to reset your password"
				+ "click the link below to change your password" + "change my  password"
				+ "ignore this email if you do remember your password,or  you have not made the request:";
		helper.setSubject(subject);
		helper.setText(content, true);

		mailSender.send(message);
	}
}
