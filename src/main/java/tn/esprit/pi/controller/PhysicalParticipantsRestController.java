package tn.esprit.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.PhysicalParticipants;
import tn.esprit.pi.service.EventService;
import tn.esprit.pi.service.PhysicalParticipantsService;

@RestController
public class PhysicalParticipantsRestController {
@Autowired
PhysicalParticipantsService physicalParticipantsService;
@Autowired
EventService eventService;
@PostMapping("/addPhysicalParticipants/{event-id}")
@ResponseBody
public void addPhyscialParticipant(@RequestBody PhysicalParticipants p,@PathVariable("event-id") String id)
{
	Event e=eventService.retriveEvent(id);
	p.setEvent(e);
	physicalParticipantsService.addPhysicalParticipant(p);
}
}
