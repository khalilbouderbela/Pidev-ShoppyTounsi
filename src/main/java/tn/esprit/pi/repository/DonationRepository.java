package tn.esprit.pi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.Donation;
import tn.esprit.pi.entities.User;



@Repository
public interface DonationRepository extends CrudRepository<Donation,Long> {
	public List<Donation> findDonationByUser(User u);
}
