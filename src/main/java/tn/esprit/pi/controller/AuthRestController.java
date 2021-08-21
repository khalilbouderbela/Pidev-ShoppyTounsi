package tn.esprit.pi.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pi.configuration.EmailConfig;
import tn.esprit.pi.entities.BlacklistToken;
import tn.esprit.pi.entities.MailHistory;
import tn.esprit.pi.entities.Role;
import tn.esprit.pi.entities.Roles;
import tn.esprit.pi.entities.ShoppingCart;
import tn.esprit.pi.entities.Tokens;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.payload.JwtResponse;
import tn.esprit.pi.payload.LoginRequest;
import tn.esprit.pi.payload.MessageResponse;
import tn.esprit.pi.payload.ResetPassword;
import tn.esprit.pi.payload.SignUpRequest;
import tn.esprit.pi.repository.BlacklistTokenRepository;
import tn.esprit.pi.repository.MailHistoryRepository;
import tn.esprit.pi.repository.RoleRepository;
import tn.esprit.pi.repository.ShoppingCartRepository;
import tn.esprit.pi.repository.TokenReopsitory;
import tn.esprit.pi.repository.UserRepository;
import tn.esprit.pi.security.JwtUtils;
import tn.esprit.pi.security.SharedLogg;
import tn.esprit.pi.security.UserDetailsImpl;
import tn.esprit.pi.service.IUserService;
import tn.esprit.pi.service.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	EmailConfig emailCfg;
	@Autowired
	UserRepository userRepository;
	@Autowired
	MailHistoryRepository mailHistoryRepo;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	IUserService iUserService;

	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	TokenReopsitory tokenReopsitory;
	@Autowired
	BlacklistTokenRepository blackListtokenReopsitory;
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
try{
		User u=userRepository.findByName(loginRequest.getUsername()).orElse(null);
		if(u.isDesactivate()){
			return ResponseEntity.badRequest().body(new MessageResponse("Error: This account is desactivate"));
		}
		if(!u.getVerified()){
			return ResponseEntity.badRequest().body(new MessageResponse("Error: This account is not verified"));
		}
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		User user=userRepository.findById(userDetails.getId()).get();
		user.setCounterLogin(0);
		user.setLastLoginDate(new Date());
		user.setConnected(true);
		userRepository.save(user);
		Tokens t = new Tokens();
		t.setName(jwt);
		t.setUserId(user.getUserId());
		tokenReopsitory.save(t);
		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
}catch (Exception e) {
	User user=userRepository.findByName(loginRequest.getUsername()).orElse(null);
	if(user!=null){
		int compt=user.getCounterLogin();
		if(compt<2){
		user.setCounterLogin(++compt);
		userRepository.save(user);
		return ResponseEntity.badRequest().body(new MessageResponse( "Error: "+ compt+" tentative(s),you have three, please try again"));
		}
		else{
			user.setDesactivate(true);
			userRepository.save(user);
			return ResponseEntity.badRequest().body(new MessageResponse("Error:  please contact our admin  "));
		}
		
	}
	
	return ResponseEntity.badRequest().body(new MessageResponse("Error:  try to push a true username and password"));
}	
}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByName(signUpRequest.getName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getAddress(), signUpRequest.getAge(),
				signUpRequest.getCin(), signUpRequest.getNumTel(),signUpRequest.getSex());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Roles> roles = new HashSet<>();

		if (strRoles== null) {
			Roles userRole = roleRepository.findByName(Role.ROLE_CLIENT)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Roles adminRole = roleRepository.findByName(Role.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "charity":
					Roles charityRole = roleRepository.findByName(Role.ROLE_CHARITYMANAGER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(charityRole);
				case "shelf":
					Roles shelfRole = roleRepository.findByName(Role.ROLE_SHELFMANAGER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(shelfRole);

					break;
				default:
					Roles userRole = roleRepository.findByName(Role.ROLE_CLIENT)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					
				
				
					roles.add(userRole);
				}
			});
		}
		user.setDateCreate(new Date());
		user.setRoles(roles);
		userRepository.save(user);
		
		if(signUpRequest.getRole()== null||signUpRequest.getRole().contains("client"))
		{
			ShoppingCart s = new ShoppingCart();
			s.setDateCreation(new Date(System.currentTimeMillis()));
			s.setUser(user);
			shoppingCartRepository.save(s);
		}
		
		int nombreAleatoire = 1000 + (int)(Math.random() * ((9999 - 1000) + 1));
	     user.setCodeVerif(nombreAleatoire);
	     user.setVerified(false);
	     userRepository.save(user);
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(this.emailCfg.getHost());
		mailSender.setPort(this.emailCfg.getPort());
		mailSender.setUsername(this.emailCfg.getUsername());
		mailSender.setPassword(this.emailCfg.getPassword());
		// Create an email instance
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("ShoppyTounsi@Gmail.com");
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("verify your email ShoppyTounsi");
		mailMessage.setText(
				"To verify your email, use this code \n" + user.getCodeVerif());
		// Send mail
		mailSender.send(mailMessage);
		Date d = new Date(System.currentTimeMillis());
		MailHistory m = new MailHistory();
		m.setDistination(user.getEmail());
		m.setBody("To verify your email, use this code \n" + user.getCodeVerif());
		m.setSendDate(d);
		m.setType("verify");
		mailHistoryRepo.save(m);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully! please verify your email http://localhost:8081/verifyEmail"));
	}

	
	
	@PostMapping("/verifyEmail/{code}")
	public String verifieAccount(@PathVariable int code) {

		Optional<User> user = userRepository.findByCodeVerif(code);
       if (user== null)
       {
    	   return "please verify the code";       }
		User resetUser = user.get();

		// Set new password
	
		// Set the reset token to null so it cannot be used again
		resetUser.setCodeVerif(0);
      resetUser.setVerified(true);
		// Save user
		userRepository.save(resetUser);
		return "votre compte est verifier avec succee";
	}
	
	
	
	
	
	@PostMapping("/batch-desactivate")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> desactivateUsersBatch() {
		//number  desactivate account
		int index=0;
		List<User> alluser= userRepository.findAll();
		for(User user: alluser){
		if(!userServiceImpl.getRoleById(user.getUserId()).contains("admin") && !user.isDesactivate()){
			Date lastlogin=user.getLastLoginDate();
			if((new Date()).getMonth()- lastlogin.getMonth()>=1 || (new Date()).getYear()- lastlogin.getYear()>=1 ){
				user.setDesactivate(true);	
				user.setConnected(false);
				userRepository.save(user);
				iUserService.setTokenToBlackList(user.getUserId());
				index++;
			}
		}
		}
		return ResponseEntity.ok(new MessageResponse(index+" are desactivate"));
	}
	
	@PostMapping("/batch-point")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> UpdatePointBatch(Authentication authentication) {
		
		int index=0;
		List<User> alluser= userRepository.findAll();
		for(User user: alluser){
			for(Roles r :user.getRoles())
			{
			
			
			
		if(!user.isDesactivate()){
			if(r.getName().equals(Role.ROLE_CLIENT))
			{
				
			
			Date datecreate=user.getDateCreate();
			Date today=new Date();
			int yearToday=today.getYear();
			if((yearToday+1900)!=user.getLastyearaddpoint()){
			
			if(today.getDate()==datecreate.getDate() && today.getMonth()==datecreate.getMonth() && today.getYear()!=datecreate.getYear() )
			{
				user.setPoint(user.getPoint()+100);
				user.setLastyearaddpoint(yearToday+1900);
				userRepository.save(user);
				index++;
			}
			}
			}
		}
		}
		}
		//add this to log
	//	UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		//SharedLogg.addlog("user", "insert",userDetails);	
		//the first is the table name, the second is the action, the third user connected
		//
		
		
		return ResponseEntity.ok(new MessageResponse("Add 100 points  for " +index+ " customer(s)"));
	}

	
	
	@GetMapping("/get_All_desactivate_Acount")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User>  get_All_desactivate_Acount() {
		
		List<User> userdesactivate=new ArrayList<User>();
		for(User user: userRepository.findAll()){
		if(user.isDesactivate()){
			userdesactivate.add(user);
		}
		}
		return userdesactivate;
	}
	
	@PostMapping("/desactivate_Acount/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public  ResponseEntity<?>  desactivate_Acount(@PathVariable("userId") long userId) {
          
           iUserService .desactivate_Acount(userId);
           iUserService.setTokenToBlackList(userId);
           return ResponseEntity.ok(new MessageResponse("User Account deactivated"));
	}
	
	@PostMapping("/activate_Acount/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public  ResponseEntity<?>  activate_Acount(@PathVariable("userId") long userId) {
          
           iUserService .activate_Acount(userId);
           return ResponseEntity.ok(new MessageResponse("User Account activated"));
	}
	
	@PostMapping(value = "/logout")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')  ")
	@ResponseBody	
		public ResponseEntity<?> logout(Authentication auth) {
			  iUserService.logout(auth);
			return  ResponseEntity.ok("loggedOut");
		}

	
}
