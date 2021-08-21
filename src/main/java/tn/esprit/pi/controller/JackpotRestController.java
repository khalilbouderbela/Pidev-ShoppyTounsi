package tn.esprit.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pi.entities.Jackpot;
import tn.esprit.pi.service.JackpotService;

@RestController
public class JackpotRestController {
@Autowired
JackpotService jackpotService;

@GetMapping("/retriveJackpot/{Jackpot-id}")
@ResponseBody
public Jackpot getEvent(@PathVariable("Jackpot-id") String id ){
	return jackpotService.retriveJackpot(id);	
}
@PostMapping("/addJackpot")
@ResponseBody
public void addJackpot(Jackpot j)
{
	  jackpotService.addJackpot(j);
	}

@PutMapping("/modifyJackpot")
@ResponseBody
public Jackpot ModifyJackpot(Jackpot j){
	return jackpotService.updateJackpot(j);
}

}
