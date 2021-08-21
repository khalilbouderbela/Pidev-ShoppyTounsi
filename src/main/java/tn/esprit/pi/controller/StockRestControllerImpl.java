package tn.esprit.pi.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pi.entities.BonCommande;
import tn.esprit.pi.entities.Entry;
import tn.esprit.pi.entities.Product;
import tn.esprit.pi.entities.Provider;
import tn.esprit.pi.security.SharedLogg;
import tn.esprit.pi.security.UserDetailsImpl;
import tn.esprit.pi.service.IStockService;

@RestController
public class StockRestControllerImpl {

	@Autowired
	IStockService iStockService;

	@GetMapping(value = "/getListMissingProduct")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<Product> getListMissigProduct() {

		return iStockService.getListMissigProduct();
	}

	@PostMapping("/addEntry")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public String addEntry(@RequestBody Entry entry) {
		
		return iStockService.addEntry(entry);
		 
	}

	@DeleteMapping("/deleteEntryById/{identry}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public String deleteEntryById(@PathVariable("identry") long entryId) {

		return iStockService.deleteEntry(entryId);

	}

	@GetMapping(value = "/getAllEntry")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<Entry> getAllEntrys() {

		return iStockService.getAllEntry();
	}

	@GetMapping(value = "/getEntryById/{identry}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public Entry getEntryById(@PathVariable("identry") long entryId) {

		if (iStockService.getEntryById(entryId) == null) {
		}

		return iStockService.getEntryById(entryId);

	}

	@GetMapping(value = "getEntryByProduct/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<Entry> getAllEntryByProduct(@PathVariable("productId") long productId) {
		return iStockService.getEntryByProduct(productId);
	}

	@GetMapping(value = "getEntryByProvider/{providerId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<Entry> getAllEntryByProvider(@PathVariable("providerId") long providerId) {
		return iStockService.getEntryByProvider(providerId);
	}

	@GetMapping(value = "getNomberEntryProvider/{providerId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public int getNomberEntryProvider(@PathVariable("providerId") long providerId) {

		return iStockService.getNomberProvider(providerId);
	}
	@GetMapping(value = "getProvidersByProduct/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<Provider> getProviderByProduct(@PathVariable("productId") long productId) {

		return iStockService.getProviderByProduct(productId);
	}
	
	@PostMapping(value = "notifyProvider/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public String notifyProvider(@PathVariable("productId") long productId) {

		 iStockService.NotifyProvider(productId);
		 return "ok";
	}
	
	@GetMapping(value = "getSumOutlay")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public int getSumOutlay() {

		return iStockService.getSumOutlay();
	}
	@GetMapping(value = "getBonCommandListOutOfDate")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<BonCommande> getBonCommandListOutOfDate() {

		return iStockService.getBonCommandListOutOfDate();
	}
	
	@GetMapping(value = "getBonCommandInProccess")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<BonCommande> getBonCommandInProccess() {

		return iStockService.getBonCommandInProccess();
	}

	@GetMapping(value = "getLastSevenDaysQuantity/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public int getLastSevenDaysQuantity(@PathVariable("productId") long productId) {

		return iStockService.getLastSevenDaysQuantity(productId);
	}
	
	
}
