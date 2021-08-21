package tn.esprit.pi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pi.entities.Donation;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.service.DonationService;

@RestController
public class DonationRestController {
@Autowired
DonationService donationService;
@GetMapping("/retriveAllDonations")
@ResponseBody
public List<Donation> getAllDonations(){
	 return donationService.retriveAllDonations();
}
@GetMapping("/retriveMyDonations")
@ResponseBody
public List<Donation> getMyDonations(Authentication auth){
	 return donationService.retriveMyDonations(auth);
}
@GetMapping("/retriveDonation/{donation-id}")
@ResponseBody
public Donation getDonation(@PathVariable("donation-id") String id ){
	return donationService.retriveDonation(id);	
}
@GetMapping("/retriveMonthDonatier")
@ResponseBody
public User getMonthDonatier(){
	return donationService.retriveDonatierOfTheMonth();	
}
@GetMapping("/retriveMonthDonation")
@ResponseBody
public Donation getMonthDonation(){
	return donationService.retriveDonationOfTheMonth();	
}
@GetMapping("/retriveAllTimeDonation")
@ResponseBody
public Donation getAllTimeDonation(){
	return donationService.retriveDonationOfAllTime();	
}
@GetMapping("/rappelDonations")
@ResponseBody
public List<String> rappelDonation() throws Exception{
	return donationService.rappelDonations();	
}
}
