package tn.esprit.pi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import tn.esprit.pi.entities.DiscountToken;
import tn.esprit.pi.entities.Donation;
import tn.esprit.pi.entities.Jackpot;
import tn.esprit.pi.entities.PhysicalParticipants;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repository.DonationRepository;
import tn.esprit.pi.repository.PhysicalParticipantsRepository;
import tn.esprit.pi.repository.UserRepository;



@Service
public class DonationService {
@Autowired
DonationRepository donationRepository;
@Autowired
EmailService emailService;
@Autowired
JackpotService jackpotService;
@Autowired
UserRepository userRepository;
@Autowired
DiscountTokenService discountTokenService;
@Autowired
PhysicalParticipantsRepository physicalParticipantsRepository ;
public List<Donation> retriveAllDonations(){
	List<Donation> donations = (List<Donation>)donationRepository.findAll();
	return donations;
}
public List<Donation> retriveMyDonations(Authentication auth){
	User u =userRepository.findByName(auth.getName()).get();
	List<Donation> donations = (List<Donation>)donationRepository.findDonationByUser(u);
	return donations;
}
public List<String> rappelDonations() throws Exception{
	Date date =new Date();
	int month = date.getMonth();
	List<Donation> rappledDonations = new ArrayList<>();
	List<String> emails = new ArrayList<>();
	List<Donation> donations = (List<Donation>)donationRepository.findAll();
	List<PhysicalParticipants>participants=(List<PhysicalParticipants>)physicalParticipantsRepository.findAll();
	for(Donation d :donations){
		if(d.getDonationDate().getMonth()-month>=1){
			rappledDonations.add(d);
		}
	}
	for(Donation d :rappledDonations){
		emails.add(d.getUser().getEmail());
	}
	for(PhysicalParticipants p :participants){
		emails.add(p.getEmail());
	}
	for(String email:emails){
	 emailService.sendMail(email.toString(), "Rappel pour sountenir la bonne cause", 
    		"Merci pour vos derniers dons mais nous vous prierons de toujours soutenir la bonne cause si vous etes deja inscrits sur nos site vous avez le bienvenue d'effectuer des dons et si vous n'etes pas priere de le faire et effectuer des dons Cordialement");
	}
    /*emailService.sendMail("wazkasmi@gmail.com", "Rappel pour sountenir la bonne cause", 
    		"<b>Bonjour</b>,<br><i>Merci pour vos derniers dons mais nous vous prierons de toujours soutenir la bonne cause si vous etes deja inscrits sur nos site vous avez le bienvenue d'effectuer des dons et si vous n'etes pas priere de le faire et effectuer des dons</i>,<br>Cordialement");
*/return emails;
}
public void addDonation(Donation d,Authentication auth){
	
	donationRepository.save(d);
	Jackpot j =jackpotService.retriveJackpot("1");
	j.setCurrentAmount(j.getCurrentAmount()+d.getAmount());
	j.setGlobalAmount(j.getGlobalAmount()+d.getAmount());
	jackpotService.updateJackpot(j);
	if(d.getAmount()>100){
		DiscountToken discountToken= new DiscountToken();
		discountToken.setUsed(false);
		discountToken.setUser(d.getUser());
		discountToken.setValue(20);
		discountTokenService.addDiscountToken(discountToken,auth);
	}else
		if(d.getAmount()>200){
			DiscountToken discountToken= new DiscountToken();
			discountToken.setUsed(false);
			discountToken.setUser(d.getUser());
			discountToken.setValue(30);
			discountTokenService.addDiscountToken(discountToken,auth);
		}else
			if(d.getAmount()>1000){
				DiscountToken discountToken= new DiscountToken();
				discountToken.setUsed(false);
				discountToken.setUser(d.getUser());
				discountToken.setValue(50);
				discountTokenService.addDiscountToken(discountToken,auth);
			}
}
public Donation updateDonation(Donation d) {
	donationRepository.save(d);
	return d ;
}

public Donation retriveDonation(String id) {
	Donation d;
	d=donationRepository.findById(Long.parseLong(id)).get();
	return d;
	
}
public User retriveDonatierOfTheMonth(){
	Date date =new Date();
	int month = date.getMonth();
	List<Donation> thisMonthDonations = new ArrayList<>();
	List<Donation> donations = (List<Donation>)donationRepository.findAll();
	for(Donation d :donations){
		if(d.getDonationDate().getMonth()==month){
			thisMonthDonations.add(d);
		}
	}
	Donation maxDonation=thisMonthDonations.get(0);
	for(Donation d :thisMonthDonations){
		if(maxDonation.getAmount()<d.getAmount())
			maxDonation=d;	
	}
	User u =maxDonation.getUser();
	return u ;
}
public Donation retriveDonationOfTheMonth(){
	Date date =new Date();
	int month = date.getMonth();
	List<Donation> thisMonthDonations = new ArrayList<>();
	List<Donation> donations = (List<Donation>)donationRepository.findAll();
	for(Donation d :donations){
		if(d.getDonationDate().getMonth()==month){
			thisMonthDonations.add(d);
		}
	}
	Donation maxDonation=thisMonthDonations.get(0);
	for(Donation d :thisMonthDonations){
		if(maxDonation.getAmount()<d.getAmount())
			maxDonation=d;	
	}
	return maxDonation;
}
public Donation retriveDonationOfAllTime(){	
	List<Donation> donations = (List<Donation>)donationRepository.findAll();	
	Donation maxDonation=donations.get(0);
	for(Donation d :donations){
		if(maxDonation.getAmount()<d.getAmount())
			maxDonation=d;	
	}
	return maxDonation;
}
}
