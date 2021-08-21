package tn.esprit.pi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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

import tn.esprit.pi.entities.Category;
import tn.esprit.pi.entities.OrderLine;
import tn.esprit.pi.entities.Orders;
import tn.esprit.pi.entities.Product;
import tn.esprit.pi.entities.Shelf;
import tn.esprit.pi.entities.ShelfRating;
import tn.esprit.pi.entities.ShelfType;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.payload.CategoryRevenulastThreeDays;
import tn.esprit.pi.payload.MessageResponse;
import tn.esprit.pi.payload.ShelfRevenu;
import tn.esprit.pi.repository.ProductRepository;
import tn.esprit.pi.repository.ShelfRepository;
import tn.esprit.pi.repository.UserRepository;
import tn.esprit.pi.security.SharedLogg;
import tn.esprit.pi.security.UserDetailsImpl;
import tn.esprit.pi.service.IShelfService;

@RestController
public class ShelfRestControllerImpl {

	@Autowired
	IShelfService ishelfService;
	@Autowired 
	ShelfRepository shelfRepo;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	UserRepository userRepository;

	// {"shelfId":1,"shelfname":"name222", "dateCreation":"2021-02-27",
	// "position":3,"type":"NORMAL","image":"iamg22"}
	@PostMapping("/addShelf")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?> addShelf(Authentication auth,@RequestBody Shelf shelf) {
		List<Shelf> shelfs = (List<Shelf>) shelfRepo.findAll();
		if(shelf.getType().equals(ShelfType.RAMADHAN))
		for (Shelf s : shelfs) {

			if (s.getType().equals(ShelfType.RAMADHAN)) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: shelf of ramadhan is already exist!"));
			}
		}
		if(shelf.getType().equals(ShelfType.PROMO))	
		{
			if(shelf.getReductionPercantage()==0)
				return ResponseEntity.badRequest().body(new MessageResponse("Add reduction percentage"));
		for (Shelf s : shelfs) {
		if (s.getType().equals(ShelfType.PROMO)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: promo is already exist!"));
		}
		}
		}
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
		SharedLogg.addlog("shelf", "add",userDetails);
		 ishelfService.addShelf(shelf);
		return ResponseEntity.ok("Shalf added shelfId :"+ shelf.getShelfId());
	}

	// {"shelfId":1,"shelfname":"name222", "dateCreation":"2021-02-27",
	// "position":3,"type":"NORMAL","image":"iamg22"}
	@PutMapping("/updateShelf")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<String> updateShelf(Authentication auth, @RequestBody Shelf shelf) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
		SharedLogg.addlog("shelf", "update",userDetails);
		ishelfService.updateShelf(shelf);
		return ResponseEntity.ok("Shalf updated");
	}

	@DeleteMapping("/deleteShelfById/{idshelf}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public String deleteEmployeById(Authentication auth, @PathVariable("idshelf") long shelfId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
		SharedLogg.addlog("shelf", "delete",userDetails);
		return ishelfService.DeleteShelfById(shelfId);

	}

	@GetMapping(value = "/getAllShelfs")
	//@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<Shelf> getAllShelfss() {

		return ishelfService.getAllShelfs();
	}


	@GetMapping(value = "/getShelfs")
	
	@ResponseBody
	public List<Shelf> getShelfs(Authentication auth) {
		if(auth!=null)
		{
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
			SharedLogg.addlog("shelf", "select",userDetails);	
		}
		return ishelfService.getShelfs(auth);
	}
	
	
	
	@GetMapping(value = "/getShelfById/{idshelf}")
	//@PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?> getShelfById(@PathVariable("idshelf") long shelfId) {

		
		
		Optional<Shelf> shelf = shelfRepo.findById(shelfId);
		if (shelf ==null)
		return  ResponseEntity.badRequest().body("not exist");
		
		return ResponseEntity.ok(shelf);
	}

	@GetMapping(value = "getNombreShelf")
	//@PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
	@ResponseBody
	public int getNombreEmployeJPQL() {

		return ishelfService.getNombreShelf();
	}

	@GetMapping("/getShelfByType/{type}")
	//@PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
	@ResponseBody
	public List<Shelf> getShelfByType(@PathVariable("type") ShelfType type) {
		return ishelfService.getShelfByType(type);

	}

	@PutMapping(value = "/affecterCategoryAShelf/{idcategory}/{idshelf}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public String affecterCategoryAShelf(Authentication auth,@PathVariable("idcategory") long categoryId,
			@PathVariable("idshelf") long shelfId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
		SharedLogg.addlog("shelf", "affecterCategory",userDetails);
		return ishelfService.affecterCategoryShelf(categoryId, shelfId);
	}
	
	@PutMapping(value = "/daffecterCategoryAShelf/{idcategory}/{idshelf}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public String daffecterCategoryAShelf(Authentication auth,@PathVariable("idcategory") long categoryId,
			@PathVariable("idshelf") long shelfId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
		SharedLogg.addlog("shelf", "daffecterCategory",userDetails);
		return ishelfService.daffecterCategoryShelf(categoryId, shelfId);
	}

	@GetMapping(value = "getAllCategoriesNameByShelfId/{idshelf}")
	//@PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
	@ResponseBody
	public List<String> getAllCategoriesNameByShelfId(@PathVariable("idshelf") int shelfId) {
		return ishelfService.getAllCategoriesNameByShelfId(shelfId);
	}

	@GetMapping(value = "getAllCategoriesByShelfId/{idshelf}")
	//@PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
	@ResponseBody
	public List<Category> getAllCategoriesByShelfId(@PathVariable("idshelf") int shelfId) {
		return ishelfService.getAllCategoryByShelfJPQL(shelfId);
	}

	@GetMapping(value = "getAllProductsByShelfId/{idshelf}")
	//@PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
	@ResponseBody
	public List<String> getAllProductsByShelfId(@PathVariable("idshelf") int shelfId) {
		return ishelfService.getAllProductByShelfJPQL(shelfId);
	}

	@PostMapping("addShelfRating/{shelfId}/{rating}")
	@PreAuthorize("hasRole('CLIENT')")
	@ResponseBody
	public ResponseEntity<?>  saveRating(Authentication auth, @PathVariable("shelfId") int shelfId,
			@PathVariable("rating") int rating) {

		if (rating>5)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("the rating must be less then 5"));
		}
		
		ishelfService.saveOrUpdateRating(auth, shelfId, rating);
		//UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
		//SharedLogg.addlog("shelfRating", "add",userDetails);
		
		return ResponseEntity.ok().body(new MessageResponse("Rating added"));

	}



	@DeleteMapping("/deleteShelfRating/{shelfId}")
	@PreAuthorize("hasRole('CLIENT')")
	public void deleteRating( Authentication auth, @PathVariable("shelfId")long shelfId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
		SharedLogg.addlog("shelfRating", "delete",userDetails);
		User u = userRepository.findByName(auth.getName()).get();
		ishelfService.deleteRating(u.getUserId(),shelfId);
	}

	@GetMapping(value = "getAllRating")
	//@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<ShelfRating> getAllRating() {
		return ishelfService.getAllRating();
	}
	@GetMapping(value = "getOrdersByUser")
	@PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
	@ResponseBody
	public List<Product> getOrders(Authentication auth) {
		return ishelfService.getOders(auth);
	}
	
	@GetMapping(value = "getRatingbyId/{idrating}")
	//@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public ShelfRating getRatingbyId(@PathVariable("idrating") long ratingId) {
		return ishelfService.getRatingbyId(ratingId);
	}

	@GetMapping(value = "getUsersRateByShelf/{idshelf}")
	//@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<User> getUSersByShelf(@PathVariable("idshelf") long idshelf) {
		return ishelfService.getUSersByShelf(idshelf);
	}

	
	@PutMapping(value = "updateShelfReduction/{idshelf}/{red}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?>  updateReduction( @PathVariable("idshelf") long shelfId,@PathVariable("red") int red) {
		Shelf shelf = shelfRepo.findById(shelfId).get();
		
	
		if(shelf.getType().equals(ShelfType.PROMO)==false)
		{
		return	ResponseEntity.badRequest().body("this shelf is not a promo shelf ");
		
		}
		List<Product> products =new ArrayList<Product>();
		
			
		for (Category c :shelf.getCategory())
		{
			for(Product p :c.getProduct())
				products.add(p);
			
		}
		for(Product p : products)
		{
			float prix =p.getPriceV() + (p.getPriceV() * shelf.getReductionPercantage()) / 100;
			float nvPrix =prix - (p.getPriceV() * red) / 100;
			if(nvPrix <p.getPriceA())
			{ 
				return ResponseEntity.badRequest().body("impossible il y a une perte dans vente de produit "+p.getName());
			}
			System.out.println(prix);
			System.out.println(nvPrix);
			p.setPriceV(nvPrix);
		    productRepository.save(p);	
		}	
		 ishelfService.UpdateReductin(red,shelfId);
		return ResponseEntity.ok().body("up ");
	}	

	@GetMapping(value = "getOrdersByShelf/{idshelf}")
	//@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<Product> getOrdersByShelf(@PathVariable("idshelf") long idshelf) {
		return ishelfService.getOrdersByShelf(idshelf);
	}


	@GetMapping(value = "getShelfRevenu")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<ShelfRevenu> getShelfsRevenu() {
		return ishelfService.getShelfsRevenu();
	}

	@GetMapping(value = "getOrdersLastThreeDays")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<Orders> getOrdersLastThreeDays() {
		return ishelfService.getOrdersLastThreeDays();
	}
	
	@GetMapping(value = "getOrdersByCategory/{catid}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<Product> getOrdersByCategory(@PathVariable("catid") long catId) {
		return ishelfService.getOrdersByCategory(catId);
	}
	
	@GetMapping(value = "getCategoryRevenu")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<CategoryRevenulastThreeDays> getCategoryRevenu() {
		return ishelfService.getCategoryLastThreeDays();
	}
	

	@DeleteMapping(value = "deleteShelfByDate")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public String deleteShelfByDate() {
		 ishelfService.deleteShelfByDate();
		 return "ok";
	}
}
