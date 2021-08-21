package tn.esprit.pi.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import tn.esprit.pi.entities.DiscountToken;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repository.DiscountTokenRepository;
import tn.esprit.pi.repository.UserRepository;



@Service
public class DiscountTokenService{
	@Autowired
DiscountTokenRepository discountTokenRepository;	
	@Autowired
	UserRepository userRepository;
	public List<DiscountToken> retriveAllDiscountTokens(){
		List<DiscountToken> discountTokens = (List<DiscountToken>)discountTokenRepository.findAll();
		return discountTokens;
	}
	public List<DiscountToken> retriveMyDiscountTokens(Authentication auth){
		User u =userRepository.findByName(auth.getName()).get();
		List<DiscountToken> discountTokens = (List<DiscountToken>)discountTokenRepository.findDiscountTokenByUser(u);
		return discountTokens;
	}
	public void addDiscountToken(DiscountToken d,Authentication auth){
		LocalDate today = LocalDate.now();
		LocalDate v = today.plusDays(120);
		ZoneId defaultZoneId=ZoneId.systemDefault();
		Date validity = Date.from(v.atStartOfDay(defaultZoneId).toInstant());
		d.setValidity(validity);
		discountTokenRepository.save(d);
	}
	public DiscountToken updateDiscounToken(DiscountToken d) {
		discountTokenRepository.save(d);
		return d ;
	}
	public void deleteDiscoutToken(long id) {
		discountTokenRepository.deleteById(id);	
		}
	public DiscountToken retriveDiscountToken(String id) {
		DiscountToken d;
		d=discountTokenRepository.findById(Long.parseLong(id)).get();
		return d;		
	}
	public int useDiscountToken(String id,int sum){
		DiscountToken d;
		
		d=discountTokenRepository.findById(Long.parseLong(id)).get();
		int discount =sum;
		if(d.isUsed()==false){
		 discount=sum-((sum*d.getValue())/100);
		d.setUsed(true);
		discountTokenRepository.save(d);
		}
		return discount ;
	}
	

}
