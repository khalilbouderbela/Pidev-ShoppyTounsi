package tn.esprit.pi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pi.entities.Provider;
import tn.esprit.pi.entities.Shelf;
import tn.esprit.pi.security.SharedLogg;
import tn.esprit.pi.security.UserDetailsImpl;
import tn.esprit.pi.service.IproviderService;

@RestController
public class ProviderRestControllerImpl {

	@Autowired
	IproviderService iProviderService;

	
	@PostMapping("/addProvider")
	//@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public Provider addProvider(@RequestBody Provider provider) {
		iProviderService.addProvider(provider);
		/*UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
		SharedLogg.addlog("provider", "add",userDetails);*/
		return provider;
	}

	@DeleteMapping("/deleteProviderById/{idProvider}")
	//@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public void deleteProviderById(@PathVariable("idProvider") long providerId) {
		iProviderService.DeleteProviderById(providerId);

	}

	@GetMapping(value = "/getAllProviders")
	//@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<Provider> getAllProviders() {

		return iProviderService.getAllProviders();
	}

	@GetMapping(value = "/getProviderById/{idProvider}")
	//@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public Provider getProviderById(@PathVariable("idProvider") long providerId) {

		return iProviderService.getProviderById(providerId);
	}
	
	@PutMapping("/updateProvider")
	//@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public Provider updateShelf(@RequestBody Provider provider) {
		iProviderService.updateProvider(provider);
		return provider;
	}
}
