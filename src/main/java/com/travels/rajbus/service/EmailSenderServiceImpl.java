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
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Random;

@Service
public class EmailSenderServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail) {
//		Random r;
		try {
//			r = SecureRandom.getInstanceStrong();
//			Integer otp = r.nextInt(10001);
//			String otpStr = otp + "";
//			if (otpStr.length() != 4) 
//				for (int x = otpStr.length(); x < 4; x++)
//					otpStr = "0" + otpStr;

			//userOtpMap.put(toEmail, otp);
			String otpStr= new DecimalFormat("000000").format(new Random().nextInt(999999));

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(toEmail);
			mailMessage.setSubject("Rajbus verification code");
			mailMessage.setText("OTP for Rajbus: " + otpStr);
			mailMessage.setFrom("mahendramahi414@gmail.com");
			mailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public void sendEmailWithAttachment(String toEmail,
                                        String body,
                                        String subject,
                                        String attachment) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("mahendramahi414@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem
                = new FileSystemResource(new File(attachment));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
                fileSystem);

        mailSender.send(mimeMessage);
      

    }
}
