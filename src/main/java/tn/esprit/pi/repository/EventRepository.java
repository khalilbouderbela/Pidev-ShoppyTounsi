package tn.esprit.pi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.User;



@Repository
public interface EventRepository extends CrudRepository<Event,Long> {
 public List<Event> findEventByUser(User u);
 public List<Event> findEventByLocation(String location);

}
