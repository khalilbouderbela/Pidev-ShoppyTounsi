package tn.esprit.pi.paypal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.Payment;

import tn.esprit.pi.entities.Donation;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.paypal.CompletePaymentRequest;
import tn.esprit.pi.paypal.CompletePaymentResponse;
import tn.esprit.pi.paypal.PaymentRequest;
import tn.esprit.pi.paypal.PaymentResponse;
import tn.esprit.pi.paypal.PaypalClient;
import tn.esprit.pi.repository.UserRepository;
import tn.esprit.pi.service.DonationService;
import tn.esprit.pi.service.UserServiceImpl;

@RestController
@RequestMapping(value = "/paypal")
public class PaypalController {
	private final PaypalClient paypalClient;
	private Map<String,Object> response = new HashMap<>();
	@Autowired
	DonationService donationService;
	@Autowired
	UserServiceImpl userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PaypalController(PaypalClient paypalClient) {
		this.paypalClient = paypalClient;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/make/payment")
	public ResponseEntity<?> makePayment(@RequestBody PaymentRequest paymentRequest,Authentication auth) {
		Date date = new Date();
		Donation d = new Donation();
		d.setAmount(Integer.parseInt(paymentRequest.getSum()));	
		User u =userRepository.findByName(auth.getName()).get();
		//d.setUser(userService.retriveUser("3"));
		d.setUser(u);

		d.setDonationDate(date);
		donationService.addDonation(d,auth);
		response = paypalClient.createPayment(paymentRequest);
		return new ResponseEntity<PaymentResponse>(new PaymentResponse((String)response.get("redirect_url"), (String)response.get("status")), 
        		HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/complete/payment")
	public ResponseEntity<?> completePayment(@RequestBody CompletePaymentRequest completePaymentRequest){
		response = paypalClient.completePayment(completePaymentRequest);
		return new ResponseEntity<CompletePaymentResponse>(new CompletePaymentResponse((String)response.get("status"), (Payment)response.get("payment")), 
        		HttpStatus.OK);
	}

}
