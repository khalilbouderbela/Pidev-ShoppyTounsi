package tn.esprit.pi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.pi.entities.PhysicalParticipants;
import tn.esprit.pi.repository.PhysicalParticipantsRepository;


@Service
public class PhysicalParticipantsService {
	@Autowired
	PhysicalParticipantsRepository physicalParticipantsRepository ;
	
	public List<PhysicalParticipants> retriveAllDonations(){
		List<PhysicalParticipants> physicalParticipants = (List<PhysicalParticipants>)physicalParticipantsRepository.findAll();
		return physicalParticipants;
	}
	public void addPhysicalParticipant(PhysicalParticipants p){
       physicalParticipantsRepository.save(p);
	}
}
