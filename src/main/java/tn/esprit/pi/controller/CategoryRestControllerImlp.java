package tn.esprit.pi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pi.entities.Category;
import tn.esprit.pi.entities.CategoryEnum;
import tn.esprit.pi.service.ICategoryService;

@RestController
@RequestMapping("/category")
public class CategoryRestControllerImlp {

	@Autowired
	private  ICategoryService iCategoryService;

	@PostMapping("/add")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {

		iCategoryService.addCategory(category);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/CategoryById/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable String id) {
	
		Category category = iCategoryService.getById(Long.parseLong(id)).get();
		return  new ResponseEntity<>(category, HttpStatus.OK);
	}

	@GetMapping("/pagination")
	public ResponseEntity<Page<Category>> getAllCategoriesByPage(Pageable pageable) {
		
		Page<Category> categories = iCategoryService.getAllCategories(pageable);
		
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> deleteCategoryById(@PathVariable String id) {

		iCategoryService.deleteById(Long.parseLong(id));
		return  ResponseEntity.ok().build();
	}

	@GetMapping("/getAllCategory")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> Categories = this.iCategoryService.getAll();
		return new ResponseEntity<>(Categories, HttpStatus.OK);
	}

	@GetMapping("categoryType/{categoryType}")
	public ResponseEntity<Category> getCategoryByName(@PathVariable CategoryEnum categoryType) {
		Category Categ = this.iCategoryService.findByCategoryType(categoryType);
		return new ResponseEntity<>(Categ, HttpStatus.OK);
	}
	@PutMapping("/update")
	public ResponseEntity<Category> update(@RequestBody Category category) {
		
		iCategoryService.updateCategory(category);

		return  new ResponseEntity<>(category, HttpStatus.OK);
	}

}
