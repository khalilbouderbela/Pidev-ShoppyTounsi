package tn.esprit.pi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.PhysicalParticipants;


@Repository
public interface PhysicalParticipantsRepository extends CrudRepository<PhysicalParticipants,Long> {
	public List<PhysicalParticipants>findPhysicalParticipantsByEvent(Event e);
}
