package tn.esprit.pi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pi.entities.DiscountToken;
import tn.esprit.pi.service.DiscountTokenService;

@RestController
public class DiscountTokenRestController {
@Autowired
DiscountTokenService discountTokenService ;
@GetMapping("/retriveAllDiscountTokens")
@ResponseBody
public List<DiscountToken> getAllDiscountokens(){
	 return discountTokenService.retriveAllDiscountTokens();
}
@GetMapping("/retriveMyDiscountTokens")
@ResponseBody
public List<DiscountToken> getMyDiscountokens(Authentication auth){
	 return discountTokenService.retriveMyDiscountTokens(auth);
}
@GetMapping("/retriveDiscountToken/{discountToken-id}")
@ResponseBody
public DiscountToken getDiscountToken(@PathVariable("discountToken-id") String id ){
	return discountTokenService.retriveDiscountToken(id);	
}
@PostMapping("/addDiscountToken")
@ResponseBody
public void addEvent(DiscountToken d,Authentication auth)
{
	discountTokenService.addDiscountToken(d,auth);
	}
@DeleteMapping("/deleteDiscountToken/{discountToken-id}")
@ResponseBody
public void removeEvent(@PathVariable("discountToken-id") String id){
	discountTokenService.deleteDiscoutToken(Long.parseLong(id));
}
@PutMapping("/modifyDiscountToken")
@ResponseBody
public DiscountToken ModifyUser(DiscountToken d) {
	return discountTokenService.updateDiscounToken(d);
}
@PutMapping("/useDiscountToken/{discountToken-id}/{sum}")
@ResponseBody
public int UseDiscountToken(@PathVariable("discountToken-id") String id,@PathVariable("sum") String sum) {
	return discountTokenService.useDiscountToken(id,Integer.parseInt(sum));
}

}
