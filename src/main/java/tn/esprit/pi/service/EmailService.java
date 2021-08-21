package tn.esprit.pi.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	 private JavaMailSender javaMailSender;
	    public EmailService(JavaMailSender javaMailSender) {
	        this.javaMailSender = javaMailSender;
	    }
	    public void sendMail(String toEmail, String subject, String message) throws Exception {

	    	/*SimpleMailMessage  mailMessage = new SimpleMailMessage();

	        mailMessage.setTo(toEmail);
	        mailMessage.setSubject(subject);
	        mailMessage.setText(message);
	        mailMessage.setFrom("wazkasmi@gmail.com");
	        javaMailSender.send(mailMessage);*/
	    	MimeMessage mes = javaMailSender.createMimeMessage();
	    	MimeMessageHelper helper = new MimeMessageHelper(mes);

	    	helper.setSubject(subject);
	    	helper.setFrom("wazkasmi@gmail.com");
	    	helper.setTo(toEmail);
	    	boolean html = true;
	    	helper.setText(message, html);

	    	javaMailSender.send(mes);
	    }
}
