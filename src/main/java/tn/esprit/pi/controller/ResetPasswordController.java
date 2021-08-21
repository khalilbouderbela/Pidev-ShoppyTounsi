package tn.esprit.pi.controller;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pi.configuration.EmailConfig;
import tn.esprit.pi.entities.MailHistory;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.payload.LoginRequest;
import tn.esprit.pi.payload.ResetPassword;
import tn.esprit.pi.repository.MailHistoryRepository;
import tn.esprit.pi.repository.UserRepository;

@RestController
public class ResetPasswordController {
	@Autowired
	EmailConfig emailCfg;
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordencoder;

	@Autowired
	MailHistoryRepository mailHistoryRepo;

	@PostMapping("/forget")
	public String processForgotPasswordForm(@RequestBody LoginRequest loginrequest, HttpServletRequest request) {

		User user = userRepository.findByName(loginrequest.getUsername()).get();

		user.setResetToken(UUID.randomUUID().toString());
		userRepository.save(user);
		String appUrl = request.getScheme() + "://" + request.getServerName();

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(this.emailCfg.getHost());
		mailSender.setPort(this.emailCfg.getPort());
		mailSender.setUsername(this.emailCfg.getUsername());
		mailSender.setPassword(this.emailCfg.getPassword());
		// Create an email instance
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("ShoppyTounsi@Gmail.com");
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Reset  Password ShoppyTounsi");
		mailMessage.setText(
				"To reset your password, use click into this url code  localhost:8081//" + user.getResetToken());
		// Send mail
		mailSender.send(mailMessage);
		Date d = new Date(System.currentTimeMillis());
		MailHistory m = new MailHistory();
		m.setDistination(user.getEmail());
		m.setBody("To reset your password, use this code \n" + user.getResetToken());
		m.setSendDate(d);
		m.setType("resetPassword");
		mailHistoryRepo.save(m);
		return user.getResetToken();
	}

	@PostMapping("/reset")
	public String setNewPassword(@Valid @RequestBody ResetPassword resetpass) {

		Optional<User> user = userRepository.findByResetToken(resetpass.getToken());

		User resetUser = user.get();

		// Set new password
		resetUser.setPassword(passwordencoder.encode(resetpass.getPassword()));
		resetUser.setDesactivate(false);
		resetUser.setCounterLogin(0);
		// Set the reset token to null so it cannot be used again
		resetUser.setResetToken(null);

		// Save user
		userRepository.save(resetUser);
		return "password updated";
	}
}
