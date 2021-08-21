package tn.esprit.pi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.pi.entities.Jackpot;
import tn.esprit.pi.repository.JackpotRepository;


@Service
public class JackpotService {
	@Autowired 
	JackpotRepository jackpotRepository;
	
	public void addJackpot(Jackpot j){
		j.setCurrentAmount(0);
		j.setGlobalAmount(0);
		j.setName("Global jackPot");
		jackpotRepository.save(j);
	}
	public Jackpot updateJackpot(Jackpot j) {
		jackpotRepository.save(j);
		return j ;
	}

	public Jackpot retriveJackpot(String id) {
		Jackpot j;
		j=jackpotRepository.findById(Long.parseLong(id)).get();
		return j;
		
	}

}

