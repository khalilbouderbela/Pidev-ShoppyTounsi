package tn.esprit.pi.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.PhysicalParticipants;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repository.UserRepository;
import tn.esprit.pi.service.EventService;

@RestController
public class EventRestController {
@Autowired
	EventService eventService;
@Autowired
UserRepository userRepository;
@GetMapping("/retriveAllEvents")
@ResponseBody
public List<Event> getEvents(){
	 return eventService.retriveAllEvents();
}
@GetMapping("/retriveMyEvents")
@ResponseBody
public List<Event> getMyEvents(Authentication auth){
	 return eventService.retriveMyEvents(auth);
}
@GetMapping("/retriveMySuggestionsEvents")
@ResponseBody
public Set<Event> getMySuggetionsEvents(Authentication auth){
	 return eventService.suggestEvent(auth);
}
@GetMapping("/retriveDispoEvents")
@ResponseBody
public List<Event> getDispoEvents(Authentication auth){
	 return eventService.retriveDispoEvents(auth);
}
@GetMapping("/retriveBestParticipantOfEvent/{event-id}")
@ResponseBody
public PhysicalParticipants getBestParticipantOfEvents(@PathVariable("event-id") String id ){
	 return eventService.retriveBestPhysicalParticipantOfTheEvent(id);
}
@GetMapping("/retriveBestEvent")
@ResponseBody
public Event getBestEvent(){
	 return eventService.retriveBestEvent();
}
@GetMapping("/retriveEvent/{event-id}")
@ResponseBody
public Event getEvent(@PathVariable("event-id") String id ){
	return eventService.retriveEvent(id);	
}
@GetMapping("/retriveEventParticipant/{event-id}")
@ResponseBody
public List<User> getEventUsers(@PathVariable("event-id") String id){
	 return eventService.retriveEventUsers(id);
}
@GetMapping("/retriveEventPhysicalParticipant/{event-id}")
@ResponseBody
public List<PhysicalParticipants> getEventPhysicalParticipants(@PathVariable("event-id") String id){
	 return eventService.retriveEventPhysicalParticipants(id);
}
@PostMapping("/addEvent")
@ResponseBody
public void addEvent(@RequestBody Event e) throws Exception
{
		e.setNumberOfVisits(0);
		e.setCollectedAmount(0);
		e.setNumberOfCurrentParticipants(0);
	  eventService.addEvent(e);
	}
@DeleteMapping("/deleteEvent/{event-id}")
@ResponseBody
public void removeEvent(@PathVariable("event-id") String id){
	eventService.deleteEvent(Long.parseLong(id));
}
@PutMapping("/modifyEvent")
@ResponseBody
public Event ModifyUser(Event e){
	return eventService.updateEvent(e);
}
@PutMapping("/participateEvent/{event-id}")
@ResponseBody
public void participateEvent(@PathVariable("event-id") String eventId,Authentication auth){
	User u =userRepository.findByName(auth.getName()).get();
	eventService.participateOnEvent(u.getUserId(),Long.parseLong(eventId));
}

@PutMapping("/closeEvent/{event-id}")
@ResponseBody
public Event closeEvent(@PathVariable("event-id") String eventId,@RequestParam("file") MultipartFile file) throws Exception {
		   return eventService.closeEvent(eventId,file);		 
}


}
