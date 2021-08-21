package tn.esprit.pi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tn.esprit.pi.entities.Category;
import tn.esprit.pi.entities.CategoryEnum;

public interface ICategoryService {

	void addCategory(Category category);

	List<Category> getAll();

    Category updateCategory(Category category);

	Optional<Category> getById(Long id);

	Category findByCategoryType(CategoryEnum categoryType);

	void deleteById(long categoryId);

	Page<Category> getAllCategories(Pageable pageable);

}
