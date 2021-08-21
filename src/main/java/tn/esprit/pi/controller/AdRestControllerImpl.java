package tn.esprit.pi.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

import tn.esprit.pi.entities.Ad;
import tn.esprit.pi.service.IAdService;

import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/ad")
public class AdRestControllerImpl {

	@Autowired
    private  IAdService iAdService;


    @GetMapping()
    public ResponseEntity<List<Ad>> allAds()
    {
    	
    	 return ResponseEntity.ok(this.iAdService.findAll());
    }
    @PostMapping("addAd")
    public ResponseEntity<String> addAd(@RequestBody Ad ad) throws MailjetSocketTimeoutException, MailjetException, JSONException
    {
    	
		return ResponseEntity.ok(this.iAdService.addAd(ad));
    }
    @PutMapping("/{id}/{quantity}")
    public ResponseEntity<Void> updateTouchedPeopleNumber(@PathVariable Long id,@PathVariable int quantity) {
        iAdService.TouchedPeopleorderLigneFromAd(id, quantity);;
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id)
    {
    	this.iAdService.deleteAd(id);
    	return ResponseEntity.ok().build();
    }
    @GetMapping("/profitability")
    public ResponseEntity<List<Map<String, String>>> isProductProfitable() {
        
        return new ResponseEntity<>(iAdService.calculateProfitability(), HttpStatus.OK);
    }
    @GetMapping("seen/{adId}")
    public ResponseEntity<Ad> markAsSeen(@PathVariable String adId)  {
        
        return ResponseEntity.ok(iAdService.markAsSeen(adId));
    }
    @PostMapping("discountMonth")
    public ResponseEntity<Void> discountMonth() throws MailjetSocketTimeoutException, MailjetException, JSONException
    {
    	this.iAdService.sendPromotionalMonth();
    	
		return ResponseEntity.ok().build();
    }
    @PostMapping("discountSchool")
    public ResponseEntity<Void> discountSchool() throws MailjetSocketTimeoutException, MailjetException, JSONException
    {
    	this.iAdService.sendPromotionalSchool();
    	
		return ResponseEntity.ok().build();
    }
    @DeleteMapping("expiredAdd")
    public ResponseEntity<String> expiredAdd()
    {
    	
    	return ResponseEntity.ok(this.iAdService.deleteExpiredAds());
    }
   

    

}



