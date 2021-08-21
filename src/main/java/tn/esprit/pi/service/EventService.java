package tn.esprit.pi.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Jackpot;
import tn.esprit.pi.entities.PhysicalParticipants;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repository.EventRepository;
import tn.esprit.pi.repository.PhysicalParticipantsRepository;
import tn.esprit.pi.repository.UserRepository;
import tn.esprit.pi.service.EmailService;
import tn.esprit.pi.service.JackpotService;
import tn.esprit.pi.service.PhysicalParticipantsService;

@Service
public class EventService {
@Autowired
EventRepository eventRepository;
@Autowired
UserRepository userRepository;
@Autowired
PhysicalParticipantsService physicalParticipantsService ;
@Autowired
JackpotService jackpotService;
@Autowired
 EmailService emailService;
@Autowired
PhysicalParticipantsRepository physicalParticipantsRepository ;
public List<Event> retriveAllEvents(){
	List<Event> events = (List<Event>)eventRepository.findAll();
	return events;
}
public List<Event> retriveMyEvents(Authentication auth){
	User u = userRepository.findByName(auth.getName()).get();
	List<Event> events = (List<Event>)eventRepository.findEventByUser(u);
	  Collections.sort(events, Event.ComparatorNumberOfVisits);

	return events;
}
public List<Event> retriveDispoEvents(Authentication auth){
	User u = userRepository.findByName(auth.getName()).get();
	List<Event> myevents = (List<Event>)eventRepository.findEventByUser(u);
	List<Event> events = (List<Event>)eventRepository.findAll();
	Date today = new Date();
	List<Event> eventsDispo = new ArrayList<Event>();
	for(Event e : events){
		if((e.getEventDate().after(today))&&(myevents.contains(e)==false)){
			eventsDispo.add(e);
		}
	}
	  Collections.sort(eventsDispo, Event.ComparatorNumberOfVisits);

	return eventsDispo;
}
public Event retriveBestEvent(){
	List<Event> events = (List<Event>)eventRepository.findAll();
	Event bestEvent= events.get(0);
	for(Event e : events){
		if((e.getCollectedAmount()/e.getEstimatedAmount())>bestEvent.getCollectedAmount()/bestEvent.getEstimatedAmount()){
			bestEvent=e;
		}
	}
	return bestEvent;
}

public List<User> retriveEventUsers(String id){
	 Event e =eventRepository.findById(Long.parseLong(id)).get();
	 List<User> users=(List<User>)userRepository.findUserByEvent(e) ;
	 return users;
}
public List<PhysicalParticipants> retriveEventPhysicalParticipants(String id){
	 Event e =eventRepository.findById(Long.parseLong(id)).get();
	 List<PhysicalParticipants> physicalParticipants=(List<PhysicalParticipants>)physicalParticipantsRepository.findPhysicalParticipantsByEvent(e) ;
	 return physicalParticipants;
}
public void addEvent(Event e) throws Exception{
	List<User>users=(List<User>)userRepository.findAll();
	List<PhysicalParticipants>participants=(List<PhysicalParticipants>)physicalParticipantsRepository.findAll();
	List<String>emails = null;
	/*for(User u:users){
		emails.add(u.getEmail());
	}
	for(PhysicalParticipants p:participants){
		emails.add(p.getEmail());
	}
	for(String email:emails){
	    emailService.sendMail(email.toString(), "Invitation pour notre evenment de charite", "nous avons l'honneur de vous inviter a notre evenment qui aura lieu "+e.getEventDate()+"au"+e.getLocation() );
	}*/
    emailService.sendMail("wazkasmi@gmail.com", "Invitation pour notre evenment de charite "+e.getName(), "<b>Bonjour</b><br>,<i>nous avons l'honneur de vous inviter a notre evenment "+e.getName()+ " qui aura lieu "+e.getEventDate()+"au"+e.getLocation()+"</i>,<br><b>Cordialement</b>" );
	eventRepository.save(e);
}
public Event updateEvent(Event e) {
	eventRepository.save(e);
	return e ;
}
public void deleteEvent(long id) {
	eventRepository.deleteById(id);	
	}
public Event retriveEvent(String id) {
	Event e;
	e=eventRepository.findById(Long.parseLong(id)).get();
	e.setNumberOfVisits(e.getNumberOfVisits()+1);
	eventRepository.save(e);
	return e;
}
@Transactional	
public void participateOnEvent(long userID, long eventId){
	
	Event event =eventRepository.findById(eventId).get();
	User user =userRepository.findById(userID).get();
	if(event.getNumberOfparticipants()>event.getNumberOfCurrentParticipants()){
	if(event.getUser()==null){
		HashSet<User>particpants = new HashSet<User>();
		particpants.add(user);
		event.setUser(particpants);
	}else{
		event.getUser().add(user);
	}
	event.setNumberOfCurrentParticipants(event.getNumberOfCurrentParticipants()+1);
	eventRepository.save(event);
	}
}
public Event closeEvent(String id,MultipartFile file ) throws Exception  {
	Event e =eventRepository.findById(Long.parseLong(id)).get();

	Path tempDir = Files.createTempDirectory("");
	File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();		
	file.transferTo(tempFile);
	Workbook workbook = WorkbookFactory.create(tempFile);
	Sheet sheet = workbook.getSheetAt(0);
	
	for(Row row :sheet){
		PhysicalParticipants p = new PhysicalParticipants();
		p.setFullname(row.getCell(0).getStringCellValue());
		p.setEmail(row.getCell(1).getStringCellValue());
		p.setSum((int)row.getCell(2).getNumericCellValue());
		p.setEvent(e);
		physicalParticipantsService.addPhysicalParticipant(p);
	}
	int sum=0;
	List<PhysicalParticipants>physicalParticipants=physicalParticipantsRepository.findPhysicalParticipantsByEvent(e);
	for(PhysicalParticipants p : physicalParticipants){
		sum=p.getSum()+sum;
	}
	Jackpot j = jackpotService.retriveJackpot("1");
	e.setCollectedAmount(sum);
	e.setNumberOfparticipants(physicalParticipants.size());
	j.setCurrentAmount(j.getCurrentAmount()+sum);
	j.setGlobalAmount(j.getGlobalAmount()+sum);
	jackpotService.updateJackpot(j);
	eventRepository.save(e);
	return e;
}
public PhysicalParticipants retriveBestPhysicalParticipantOfTheEvent(String id ){
	Event e =eventRepository.findById(Long.parseLong(id)).get();
	List<PhysicalParticipants>physicalParticipants=physicalParticipantsRepository.findPhysicalParticipantsByEvent(e);
	PhysicalParticipants bestparticipant=physicalParticipants.get(0);

	for(PhysicalParticipants p : physicalParticipants){
		if(p.getSum()>bestparticipant.getSum()){
			bestparticipant=p;
		}
	}
	return bestparticipant;	

}
public Set<Event> suggestEvent(Authentication auth){
	User u = userRepository.findByName(auth.getName()).get();
	List<Event> events = (List<Event>)eventRepository.findEventByUser(u);
    Hashtable<String, Integer> locations = new Hashtable<String, Integer>();
    for(Event e:events){
    	if(locations.containsKey(e.getLocation())){
    		locations.replace(e.getLocation(),locations.get(e.getLocation())+1);
    	}else{
    		locations.put(e.getLocation(), 1);
    	}
    	}
    Set s = locations.keySet();
    Iterator itr = s.iterator();
    String key="";
    String max=(String) itr.next();
    List<String> maxevents = null;
    while (itr.hasNext()) {
        key = (String) itr.next();
    	if(locations.get(key)>locations.get(max)){
    		max=key; 	
        
    	}
    }
    List<Event> allSuggestionEvents =eventRepository.findEventByLocation(max);
    

    Date today = new Date();
	List<Event> dispoSugesstionEvents = new ArrayList<Event>();
	Set<Event> dispoevent = new HashSet<Event>();
	for(Event e : allSuggestionEvents){
		if((e.getEventDate().after(today))&&(events.contains(e)==false)){			
			//dispoSugesstionEvents.add(e);
			dispoevent.add(e);
		}
		
	}
    return dispoevent;

    
}
}
