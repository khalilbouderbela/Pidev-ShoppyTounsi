package tn.esprit.pi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pi.entities.Logg;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.payload.JwtResponse;
import tn.esprit.pi.payload.MessageResponse;
import tn.esprit.pi.security.UserDetailsImpl;
import tn.esprit.pi.service.LoggService;

@RestController
public class LoggController {
	@Autowired
	LoggService loggservice;
	
	@PostMapping("/add-logg/{actionName}/{tableName}/{idUser}")
	public String addlogg( @PathVariable("actionName")String actionName, @PathVariable("tableName")String tableName,@PathVariable("idUser")long idUser){
		try{
		
		
		Logg logg=new Logg();
		logg.setTableName(tableName);
		logg.setActionName(actionName);
		logg.setDateAction(new Date());
		
		logg.setUserId(idUser);
		loggservice.addLogg(logg);
		return "OK";
		}catch(Exception e){
		return	"ERROR";
		}
	}
	
	
	@GetMapping("/get_All_Logg")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Logg>  get_All_Logg() {
		
		//administrator
		return loggservice.getAllLogg();
	}
	
	@GetMapping("/get_All_Logg_User")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Logg>  get_All_Logg_User(Authentication authentication) {
		try{
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			if(userDetails!=null){
		
		return loggservice.getLoggByUser(userDetails.getId());
			}
		}catch (Exception e) {
			return null;
		}
		return null;
	}
	
	@GetMapping("/get_All_Logg_ByUserId/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Logg>  get_All_Logg_User(@PathVariable("userId")long userId) {

		
		return loggservice.getLoggByUser(userId);
		
	}
}
